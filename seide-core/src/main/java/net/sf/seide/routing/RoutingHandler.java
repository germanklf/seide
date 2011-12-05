package net.sf.seide.routing;

import net.sf.seide.event.Event;
import net.sf.seide.event.EventHandler;
import net.sf.seide.stages.RoutingOutcome;
import net.sf.seide.support.Beta;

@Beta
public interface RoutingHandler {

    /**
     * Registers a source Event, any implementation should take into account the tracking of this source events to route
     * the message.<br/>
     * Fired just before starting the event.
     * 
     * @param source
     */
    void start(Event source, EventHandler<?> eventHandler);

    /**
     * Registers a source Event and return the handled version of it. The instance returned is which will be used for
     * further processing, not the original.
     * 
     * @param source
     * @param eventHandler
     */
    Event register(Event source, EventHandler<?> eventHandler);

    /**
     * 
     * @param source
     * @param eventHandler
     * @param out
     * @return
     */
    RoutingOutcome handle(Event source, EventHandler<?> eventHandler, RoutingOutcome out);

    /**
     * 
     * @param source
     * @param eventHandler
     * @param out
     */
    void finish(Event source, EventHandler<?> eventHandler, RoutingOutcome out);

    /**
     * @return the parent {@link RoutingHandler} if applies.
     */
    RoutingHandler getParent();

}
