package net.sf.seide.event;

import net.sf.seide.message.JoinHandler;
import net.sf.seide.message.Message;
import net.sf.seide.support.Beta;

@Beta
public class JoinEvent
    extends Event {

    private final JoinHandler joinHandler;

    public JoinEvent(Event event, JoinHandler joinHandler) {
        super(event.stage, event.message);
        this.joinHandler = joinHandler;
    }

    public JoinEvent(String stage, Message message, JoinHandler joinHandler) {
        super(stage, message);
        this.joinHandler = joinHandler;
    }

    public JoinHandler getJoinHandler() {
        return this.joinHandler;
    }

}
