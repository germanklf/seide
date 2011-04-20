package net.sf.seide.stages;

/**
 * {@link EventHandler} represents the unit of work behind the {@link Stage}.
 * 
 * @author german.kondolf
 * @see {@link Data}
 * @see {@link RoutingOutcome}
 */
public interface EventHandler {

    /**
     * Execution of {@link Stage} {@link EventHandler}.
     * 
     * @param data payload received.
     * @return {@link RoutingOutcome} specifiying the next {@link Stage} and the {@link Data} passsed to it or them.
     *         Null return value is allowed, mainly to mark the end of a execution path.
     */
    RoutingOutcome execute(Data data);

}
