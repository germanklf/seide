package net.sf.seide.core;

import java.util.List;

import net.sf.seide.event.Event;
import net.sf.seide.stages.Data;
import net.sf.seide.stages.EventHandler;
import net.sf.seide.stages.Stage;

/**
 * {@link Dispatcher} is the basic interface for the entry point to route messages throught the {@link Stage}s.
 * 
 * @author german.kondolf
 * @see {@link Stage}
 * @see {@link EventHandler}
 * @see {@link Data}
 */
public interface Dispatcher
    extends Lifecycle {

    /**
     * Routes the {@link Data} to the specified {@link Stage} executing the associated {@link EventHandler}.
     * 
     * @param stage {@link Stage#id}
     * @param data payload.
     */
    void execute(String stage, Data data);

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
