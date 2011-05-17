package net.sf.seide.stages;

import net.sf.seide.event.Event;
import net.sf.seide.event.EventHandler;
import net.sf.seide.support.Beta;

@Beta
public interface RoutingHandler {

    void startProcessing(Event source, EventHandler<?> eventHandler);

    RoutingOutcome processOutcome(Event source, EventHandler<?> eventHandler, RoutingOutcome out);

    void processFinalization(Event source, EventHandler<?> eventHandler, RoutingOutcome out);

}
