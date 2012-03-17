package org.seideframework.core;

import java.util.concurrent.ThreadPoolExecutor;

import org.seideframework.controller.StageController;
import org.seideframework.core.impl.DispatcherImpl;
import org.seideframework.event.EventHandler;
import org.seideframework.stages.Stage;
import org.seideframework.stages.StageStatistics;
import org.seideframework.stages.impl.StageStatisticsImpl;


/**
 * {@link RuntimeStage} represents the {@link DispatcherImpl} internal configuration metadata to route, track stats,
 * execute.
 * 
 * @see {@link Stage}
 * @see {@link EventHandler}
 * @see {@link StageStatistics}
 * @see {@link ThreadPoolExecutor}
 * @author german.kondolf
 */
public class RuntimeStage {

    private final Stage stage;
    private final String context;
    private final String id;
    @SuppressWarnings("rawtypes")
    private final EventHandler eventHandler;
    private final StageStatistics stageStats;
    private final StageStatistics routingStageStats;
    // private ExecutorService executor;

    private final StageController controller;

    public RuntimeStage(Stage stage) {
        this.stage = stage;
        this.context = stage.getContext();
        this.id = stage.getId();
        this.eventHandler = stage.getEventHandler();
        this.stageStats = new StageStatisticsImpl(this.context, this.id);
        this.routingStageStats = new StageStatisticsImpl(this.context, this.id);
        this.controller = stage.getController();
    }

    public Stage getStage() {
        return this.stage;
    }

    public String getId() {
        return this.id;
    }

    public String getContext() {
        return this.context;
    }

    @SuppressWarnings("rawtypes")
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public StageStatistics getStageStats() {
        return this.stageStats;
    }

    public StageStatistics getRoutingStageStats() {
        return this.routingStageStats;
    }

    // public void setExecutor(ThreadPoolExecutor executor) {
    // this.executor = executor;
    // }
    //
    // public ExecutorService getExecutor() {
    // return this.executor;
    // }

    public StageController getController() {
        return this.controller;
    }

}
