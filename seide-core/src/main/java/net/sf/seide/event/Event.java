package net.sf.seide.event;

import net.sf.seide.message.JoinHandler;
import net.sf.seide.message.Message;
import net.sf.seide.stages.Stage;

/**
 * The {@link EventHandler} represents the pair of {@link Stage} identifier and the proper {@link Message} associated
 * for an execution.
 * 
 * @author german.kondolf
 * @see {@link Message}
 * @see {@link Stage}
 */
public class Event {

    protected final String stage;
    protected final Message message;
    protected final JoinHandler joinHandler;

    public Event(String stage, Message message) {
        this(stage, message, null);
    }

    public Event(Event event, JoinHandler joinHandler) {
        this(event.getStage(), event.getMessage(), joinHandler);
    }

    public Event(String stage, Message message, JoinHandler joinHandler) {
        this.stage = stage;
        this.message = message;
        this.joinHandler = joinHandler;
    }

    public String getStage() {
        return this.stage;
    }

    public Message getMessage() {
        return this.message;
    }

    public JoinHandler getJoinHandler() {
        return this.joinHandler;
    }

}
