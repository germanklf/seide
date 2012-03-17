package org.seideframework.event;

import org.seideframework.message.Message;
import org.seideframework.stages.RoutingOutcome;
import org.seideframework.support.Beta;

@Beta
public interface InterruptableEventHandler<T extends Message>
    extends EventHandler<T> {

    RoutingOutcome aborted(T message, Throwable exception, boolean externalReason);

}
