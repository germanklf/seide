package net.sf.seide.stages;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.RuntimeStage;
import net.sf.seide.event.Event;

public class RunnableEventHandlerWrapper
    implements Runnable {

    private final Dispatcher dispatcher;
    private final RuntimeStage runtimeStage;
    private final Data data;
    private final StageStatistics stageStats;
    private final StageStatistics routingStageStats;

    public RunnableEventHandlerWrapper(Dispatcher dispatcher, RuntimeStage runtimeStage, Data data) {
        this.dispatcher = dispatcher;
        this.runtimeStage = runtimeStage;
        this.data = data;
        this.stageStats = runtimeStage.getStageStats();
        this.routingStageStats = runtimeStage.getRoutingStageStats();

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
        RoutingOutcome routingOutcome = this.runtimeStage.getEventHandler().execute(this.data);

        // tracking & remove running...
        this.stageStats.trackTimeAndExecution(System.nanoTime() - time);
        this.stageStats.removeRunning();

        // now track the routing times...
        this.routingStageStats.addRunning();
        time = System.nanoTime();
        if (routingOutcome != null && !routingOutcome.isEmpty()) {
            for (Event e : routingOutcome.getEvents()) {
                this.dispatcher.execute(e);
            }
        }
        this.routingStageStats.trackTimeAndExecution(System.nanoTime() - time);
        this.routingStageStats.removeRunning();
    }

}
