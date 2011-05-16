package net.sf.seide.core;

import java.util.List;

import net.sf.seide.event.Event;
import net.sf.seide.event.EventHandler;
import net.sf.seide.message.Message;
import net.sf.seide.stages.Stage;

/**
 * {@link Dispatcher} is the basic interface for the entry point to route messages through the {@link Stage}s.
 * 
 * @author german.kondolf
 * @see {@link Stage}
 * @see {@link EventHandler}
 * @see {@link Message}
 */
public interface Dispatcher
    extends Lifecycle {

    /**
     * Routes the {@link Message} to the specified {@link Stage} executing the associated {@link EventHandler}.
     * 
     * @param stage {@link Stage#id}
     * @param message payload.
     */
    void execute(String stage, Message message);

    /**
     * Routes the {@link Event} to the specified {@link Stage} executing the associated {@link EventHandler}.
     * 
     * @param stage {@link Stage#id}
     * @param data payload.
     */
    void execute(Event event);

    /**
     * {@link Stage} list of supported flow {@link EventHandler}.
     * 
     * @param stages {@link Stage} list.
     */
    void setStages(List<Stage> stages);

    /**
     * @return The dispatcher context identifier.
     */
    String getContext();

    /**
     * This parameter sets a context, to allow different flows running at the same time and it is propagated in logs &
     * JXM metrics.
     * 
     * @param context
     */
    void setContext(String context);

}
