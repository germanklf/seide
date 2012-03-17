package org.seideframework.core;

import java.util.List;

import org.seideframework.event.Event;
import org.seideframework.event.EventHandler;
import org.seideframework.message.Message;
import org.seideframework.stages.Stage;


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
     * @throws InvalidStageException
     */
    boolean execute(String stage, Message message) throws InvalidStageException;

    /**
     * Routes the {@link Event} to the specified {@link Stage} executing the associated {@link EventHandler}.
     * 
     * @param stage {@link Stage#id}
     * @param data payload.
     * @throws InvalidStageException
     */
    boolean execute(Event event) throws InvalidStageException;

    /**
     * {@link Stage} list of supported flow {@link EventHandler}.
     * 
     * @param stages {@link Stage} list.
     */
    void setStages(List<Stage> stages);

    /**
     * Gets the configured list of {@link Stage}s.
     * 
     * @return immutable list of mutable {@link Stage}s.
     */
    List<Stage> getStages();

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
