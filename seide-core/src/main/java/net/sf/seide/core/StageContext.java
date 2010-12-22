package net.sf.seide.core;

import java.util.concurrent.ThreadPoolExecutor;

import net.sf.seide.stages.Event;
import net.sf.seide.stages.StageStatistics;
import net.sf.seide.stages.impl.StageStatisticsImpl;

public class StageContext {

    private final String context;
    private final String id;
    private final Event event;
    private final StageStatistics stageStats;
    private final StageStatistics routingStageStats;
    private ThreadPoolExecutor executor;

    public StageContext(Event event) {
        this.context = event.getStage().getContext();
        this.id = event.getStage().getId();
        this.event = event;
        this.stageStats = new StageStatisticsImpl(this.context, this.id);
        this.routingStageStats = new StageStatisticsImpl(this.context, this.id);
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
