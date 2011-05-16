package net.sf.seide.event;

import net.sf.seide.message.Message;
import net.sf.seide.stages.RoutingOutcome;
import net.sf.seide.support.Beta;

@Beta
public interface InterruptableEventHandler<T extends Message>
    extends EventHandler<T> {

    RoutingOutcome aborted(T message, Throwable exception, boolean externalReason);

}
