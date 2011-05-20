package net.sf.seide.core.impl;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import net.sf.seide.controller.StageController;
import net.sf.seide.core.ConfigurationException;
import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.DispatcherAware;
import net.sf.seide.core.DispatcherStatistics;
import net.sf.seide.core.JMXHelper;
import net.sf.seide.core.RuntimeStage;
import net.sf.seide.event.Event;
import net.sf.seide.event.EventHandler;
import net.sf.seide.message.Message;
import net.sf.seide.stages.Stage;
import net.sf.seide.stages.StageAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main {@link Dispatcher} implementation.<br/>
 * 
 * @author german.kondolf
 */
public class DispatcherImpl
    implements Dispatcher, DispatcherStatistics {

    private static final String DISPATCHER_MXBEAN_PREFIX = "net.sf.seide.core.impl:type=DispatcherImpl,name=dispatcher-";
    private static final String STAGE_MXBEAN_PREFIX = "net.sf.seide.stage:type=Stage,name=stage-";

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherImpl.class);

    // stages map
    private Map<String, RuntimeStage> stagesMap;

    private String context;
    private List<Stage> stages;

    // control variables
    private volatile boolean started = false;
    private volatile boolean shutdownRequired = false;

    // statistics
    private AtomicLong eventExecutionCount = new AtomicLong(0);

    public void execute(String stage, Message message) {
        this.execute(new Event(stage, message));
    }

    public void execute(Event event) {
        final String stage = event.getStage();

        if (this.shutdownRequired) {
            LOGGER.info("Stage execution rejected for stage [" + stage + "], shutdown required!");
            return;
        }

        RuntimeStage runtimeStage = this.stagesMap.get(stage);
        if (runtimeStage == null) {
            throw new RuntimeException("Stage [" + stage + "] is undefined.");
        }

        // delegate the execution to the underlying stage-controller
        runtimeStage.getController().execute(event);

        // track!
        this.eventExecutionCount.incrementAndGet();
    }

    public void start() {
        // register jmx server
        JMXHelper.registerMXBean(this, DISPATCHER_MXBEAN_PREFIX + this.context);

        this.stagesMap = new LinkedHashMap<String, RuntimeStage>(this.stages.size());

        for (Stage stage : this.stages) {
            final String stageId = stage.getId();
            final RuntimeStage runtimeStage = new RuntimeStage(stage);

            // JMX, everybody loves JMX!
            JMXHelper.registerMXBean(runtimeStage.getStageStats(), STAGE_MXBEAN_PREFIX + this.context + "-" + stageId);
            JMXHelper.registerMXBean(runtimeStage.getRoutingStageStats(), STAGE_MXBEAN_PREFIX + this.context + "-routing-"
                + stageId);

            // minimal validation
            EventHandler<?> eventHandler = stage.getEventHandler();
            if (eventHandler == null) {
                throw new ConfigurationException(MessageFormat.format(
                    "EventHandler cannot be null, invalid configuration for stage [{0}@{1}]", stage.getId(), this.context));
            }

            // dependency injection
            if (eventHandler instanceof StageAware) {
                ((StageAware) eventHandler).setStage(stage);
            }
            if (eventHandler instanceof DispatcherAware) {
                ((DispatcherAware) eventHandler).setDispatcher(this);
            }

            // start the stage-controller
            StageController stageController = runtimeStage.getController();
            stageController.setDispatcher(this);
            stageController.setRuntimeStage(runtimeStage);
            stageController.start();

            this.stagesMap.put(stageId, runtimeStage);
        }

        this.started = true;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void stop() {
        this.shutdownRequired = true;

        // shutdown the threadpools...
        for (Entry<String, RuntimeStage> entry : this.stagesMap.entrySet()) {
            String stage = entry.getKey();
            RuntimeStage runtimeStage = entry.getValue();

            runtimeStage.getController().stop();

            LOGGER.info("Stopping stage-controller for [" + stage + "]");
        }

        // clean up
        for (Entry<String, RuntimeStage> entry : this.stagesMap.entrySet()) {
            String stageId = entry.getKey();
            JMXHelper.unregisterMXBean(STAGE_MXBEAN_PREFIX + this.context + "-" + stageId);
            JMXHelper.unregisterMXBean(STAGE_MXBEAN_PREFIX + this.context + "-routing-" + stageId);
        }
        JMXHelper.unregisterMXBean(DISPATCHER_MXBEAN_PREFIX + this.context);

        this.started = false;
        this.shutdownRequired = false;
    }

    @Override
    public boolean isRunning() {
        return this.started && !this.shutdownRequired;
    }

    @Override
    public int getStageCount() {
        return this.stagesMap.size();
    }

    @Override
    public long getTotalEventExecutionsCount() {
        return this.eventExecutionCount.get();
    }

}
