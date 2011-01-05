package net.sf.seide.core;

import java.util.concurrent.ThreadPoolExecutor;

import net.sf.seide.core.impl.DispatcherImpl;
import net.sf.seide.stages.Event;
import net.sf.seide.stages.Stage;
import net.sf.seide.stages.StageStatistics;
import net.sf.seide.stages.impl.StageStatisticsImpl;

/**
 * {@link StageContext} represents the {@link DispatcherImpl} internal configuration metadata to route, track stats,
 * execute.
 * 
 * @see {@link Stage}
 * @see {@link Event}
 * @see {@link StageStatistics}
 * @see {@link ThreadPoolExecutor}
 * @author german.kondolf
 */
public class StageContext {

    private final Stage stage;
    private final String context;
    private final String id;
    private final Event event;
    private final StageStatistics stageStats;
    private final StageStatistics routingStageStats;
    private ThreadPoolExecutor executor;

    public StageContext(Stage stage) {
        this.stage = stage;
        this.context = stage.getContext();
        this.id = stage.getId();
        this.event = stage.getEvent();
        this.stageStats = new StageStatisticsImpl(this.context, this.id);
        this.routingStageStats = new StageStatisticsImpl(this.context, this.id);
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

    public Event getEvent() {
        return this.event;
    }

    public StageStatistics getStageStats() {
        return this.stageStats;
    }

    public StageStatistics getRoutingStageStats() {
        return this.routingStageStats;
    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    public ThreadPoolExecutor getExecutor() {
        return this.executor;
    }

}
