package net.sf.seide.core.impl;

import java.lang.management.ManagementFactory;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.sf.seide.core.ConfigurationException;
import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.DispatcherAware;
import net.sf.seide.core.DispatcherStatistics;
import net.sf.seide.core.StageContext;
import net.sf.seide.stages.Data;
import net.sf.seide.stages.Event;
import net.sf.seide.stages.RunnableEventWrapper;
import net.sf.seide.stages.Stage;
import net.sf.seide.stages.StageAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherImpl
    implements Dispatcher, DispatcherStatistics {

    private static final String DISPATCHER_MXBEAN_PREFIX = "net.sf.seide.core.impl:type=DispatcherImpl,name=dispatcherImpl-";
    private static final String STAGE_MXBEAN_PREFIX = "net.sf.seide.stage:type=Stage,name=";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, StageContext> stagesMap;

    private String context;
    private List<Stage> stages;

    // control variables
    private volatile boolean shutdownRequired = false;

    // statistics
    private AtomicLong eventExecutionCount = new AtomicLong(0);

    public void execute(String stage, Data data) {
        if (this.shutdownRequired) {
            this.logger.info("Stage execution rejected for stage [" + stage + "], shutdown required!");
            return;
        }

        StageContext stageContext = this.stagesMap.get(stage);
        if (stageContext == null) {
            throw new RuntimeException("Stage [" + stage + "] is undefined.");
        }

        RunnableEventWrapper runnableEventWrapper = new RunnableEventWrapper(this, stageContext, data);

        Executor executor = stageContext.getExecutor();
        executor.execute(runnableEventWrapper);

        // track!
        this.eventExecutionCount.incrementAndGet();
    }

    public void start() {
        // register jmx server
        this.registerMXBean(this, DISPATCHER_MXBEAN_PREFIX + this.context);

        this.stagesMap = new LinkedHashMap<String, StageContext>(this.stages.size());

        for (Stage stage : this.stages) {
            final String stageId = stage.getId();

            BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(stage.getCoreThreads(), stage.getMaxThreads(), 0L,
                TimeUnit.MILLISECONDS, queue, new ThreadFactory() {

                    private final AtomicLong threadNumber = new AtomicLong(0);

                    public Thread newThread(Runnable r) {
                        return new Thread(r, DispatcherImpl.this.context.toUpperCase() + "_" + stageId.toUpperCase()
                            + "_ST#" + this.threadNumber.incrementAndGet());
                    }
                });

            StageContext stageContext = new StageContext(stage);
            stageContext.setExecutor(executor);

            // JMX, everybody loves JMX!
            this.registerMXBean(stageContext.getStageStats(), STAGE_MXBEAN_PREFIX + this.context + "-" + stageId);
            this.registerMXBean(stageContext.getRoutingStageStats(), STAGE_MXBEAN_PREFIX + this.context + "-routing-"
                + stageId);

            // minimal validation
            Event event = stage.getEvent();
            if (event == null) {
                throw new ConfigurationException(MessageFormat.format(
                    "Event cannot be null, invalid configuration for stage [{0}@{1}]", stage.getId(), this.context));
            }

            // dependency injection
            if (event instanceof StageAware) {
                ((StageAware) event).setStage(stage);
            }
            if (event instanceof DispatcherAware) {
                ((DispatcherAware) event).setDispatcher(this);
            }

            this.stagesMap.put(stageId, stageContext);
        }
    }

    private void registerMXBean(Object object, String name) {
        this.logger.info("Registering MBean [" + name + "]...");
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            mbs.registerMBean(object, new ObjectName(name));
        } catch (Exception e) {
            this.logger.error("Error registering MBean: [" + name + "]", e);
        }
    }

    private void unregisterMXBean(String name) {
        this.logger.info("Unregistering MBean [" + name + "]...");
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            mbs.unregisterMBean(new ObjectName(name));
        } catch (Exception e) {
            this.logger.error("Error unregistering MBean: [" + name + "]", e);
        }
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void shutdown() {
        this.shutdownRequired = true;

        // shutdown the threadpools...
        for (Entry<String, StageContext> entry : this.stagesMap.entrySet()) {
            String stage = entry.getKey();
            ThreadPoolExecutor tpe = entry.getValue().getExecutor();
            List<Runnable> remaining = tpe.shutdownNow();
            int remainingCount = remaining != null ? remaining.size() : 0;

            this.logger.info("Shutdown processed for [" + stage + "], remaining runnables: " + remainingCount);
        }

        // clean up
        for (Entry<String, StageContext> entry : this.stagesMap.entrySet()) {
            String stageId = entry.getKey();
            this.unregisterMXBean(STAGE_MXBEAN_PREFIX + this.context + "-" + stageId);
            this.unregisterMXBean(STAGE_MXBEAN_PREFIX + this.context + "-routing-" + stageId);
        }
        this.unregisterMXBean(DISPATCHER_MXBEAN_PREFIX + this.context);
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
