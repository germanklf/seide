package org.seideframework.event;

import java.util.UUID;

import org.seideframework.message.JoinHandler;
import org.seideframework.message.Message;
import org.seideframework.stages.Stage;


/**
 * The {@link EventHandler} represents the pair of {@link Stage} identifier and the proper {@link Message} associated
 * for an execution.
 * 
 * @author german.kondolf
 * @see {@link Message}
 * @see {@link Stage}
 */
public class Event {

    protected final String id;
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
        this.id = UUID.randomUUID().toString(); // experimental by now.
    }

    public String getId() {
        return this.id;
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

    @Override
    public String toString() {
        return "Event[stage:" + this.stage + "; id:" + this.id + "]";
    }
}
