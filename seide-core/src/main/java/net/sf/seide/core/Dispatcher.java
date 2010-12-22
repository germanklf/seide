package net.sf.seide.core;

import java.util.List;

import net.sf.seide.stages.Data;
import net.sf.seide.stages.Event;
import net.sf.seide.stages.Stage;

/**
 * {@link Dispatcher} is the basic interface for the entry point to route messages throught the {@link Stage}s.
 * 
 * @author german.kondolf
 * @see {@link Stage}
 * @see {@link Event}
 * @see {@link Data}
 */
public interface Dispatcher {

    /**
     * Starts & configures the {@link Dispatcher} instance.
     */
    void start();

    /**
     * Routes the {@link Data} to the specified {@link Stage} executing the associated {@link Event}.
     * 
     * @param stage {@link Stage#id}
     * @param data payload.
     */
    void execute(String stage, Data data);

    /**
     * Shutdown & clean up method. The invocation dries out the instance, it's not supposed to be re-starteable by
     * {@link Dispatcher#start()}
     */
    void shutdown();

    /**
     * {@link Stage} list of supported flow {@link Event}.
     * 
     * @param stages {@link Stage} list.
     */
    void setStages(List<Stage> stages);

}
