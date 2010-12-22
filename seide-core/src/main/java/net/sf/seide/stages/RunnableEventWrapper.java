package net.sf.seide.stages;

import java.util.List;
import java.util.Map.Entry;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.StageContext;

public class RunnableEventWrapper
    implements Runnable {

    private final Dispatcher dispatcher;
    private final StageContext stageContext;
    private final Data data;
    private final StageStatistics stageStats;
    private final StageStatistics routingStageStats;

    public RunnableEventWrapper(Dispatcher dispatcher, StageContext stageContext, Data data) {
        this.dispatcher = dispatcher;
        this.stageContext = stageContext;
        this.data = data;
        this.stageStats = stageContext.getStageStats();
        this.routingStageStats = stageContext.getRoutingStageStats();

        // asume that a creating means a pending...
        this.stageStats.addPending();
    }

    @Override
    public void run() {
        // no longer pending...
        this.stageStats.removePending();
        // now running...
        this.stageStats.addRunning();

        long time = 0;
        time = System.nanoTime();

        // execution
        RoutingOutcome routingOutcome = this.stageContext.getEvent().execute(this.data);

        // tracking & remove running...
        this.stageStats.trackTimeAndExecution(System.nanoTime() - time);
        this.stageStats.removeRunning();

        // now track the routing times...
        this.routingStageStats.addRunning();
        time = System.nanoTime();
        if (routingOutcome != null && !routingOutcome.isEmpty()) {
            for (Entry<String, List<Data>> events : routingOutcome.getOutput().entrySet()) {
                for (Data event : events.getValue()) {
                    this.dispatcher.execute(events.getKey(), event);
                }
            }
        }
        this.routingStageStats.trackTimeAndExecution(System.nanoTime() - time);
        this.routingStageStats.removeRunning();
    }

}
