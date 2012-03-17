package org.seideframework.ext.spring3;

import org.seideframework.event.EventHandler;
import org.seideframework.message.Message;
import org.seideframework.stages.RoutingOutcome;

public class MockEvent
    implements EventHandler<Message> {

    @Override
    public RoutingOutcome execute(Message message) {
        return null;
    }

}
