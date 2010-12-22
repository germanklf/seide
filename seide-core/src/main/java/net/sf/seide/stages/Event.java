package net.sf.seide.stages;

public interface Event {

    RoutingOutcome execute(Data data);

    Stage getStage();

}
