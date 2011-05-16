package net.sf.seide.event;

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

    public Event(String stage, Message message) {
        this.stage = stage;
        this.message = message;
    }

    public String getStage() {
        return this.stage;
    }

    public Message getMessage() {
        return this.message;
    }

}
