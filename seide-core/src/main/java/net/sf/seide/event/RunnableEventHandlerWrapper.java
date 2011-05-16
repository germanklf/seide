package net.sf.seide.event;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.RuntimeStage;
import net.sf.seide.message.Message;
import net.sf.seide.stages.RoutingOutcome;
import net.sf.seide.stages.StageStatistics;

public class RunnableEventHandlerWrapper
    implements Runnable {

    private final Dispatcher dispatcher;
    private final RuntimeStage runtimeStage;
    private final Message message;
    private final StageStatistics stageStats;
    private final StageStatistics routingStageStats;

    public RunnableEventHandlerWrapper(Dispatcher dispatcher, RuntimeStage runtimeStage, Message message) {
        this.dispatcher = dispatcher;
        this.runtimeStage = runtimeStage;
        this.message = message;
        this.stageStats = runtimeStage.getStageStats();
        this.routingStageStats = runtimeStage.getRoutingStageStats();

        // asume that a creating means a pending...
        this.stageStats.addPending();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        // no longer pending...
        this.stageStats.removePending();
        // now running...
        this.stageStats.addRunning();

        long time = 0;
        time = System.nanoTime();

        // execution
        RoutingOutcome routingOutcome = this.runtimeStage.getEventHandler().execute(this.message);

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
