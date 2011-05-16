package net.sf.seide.ext.spring3;

import net.sf.seide.event.EventHandler;
import net.sf.seide.message.Message;
import net.sf.seide.stages.RoutingOutcome;

public class MockEvent
    implements EventHandler<Message> {

    @Override
    public RoutingOutcome execute(Message message) {
        return null;
    }

}
