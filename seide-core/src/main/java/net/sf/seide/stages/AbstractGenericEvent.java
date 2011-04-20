package net.sf.seide.stages;

/**
 * This abstraction of {@link EventHandler} just casts the value received from parameter as {@link Data} to the given
 * parametric type.
 * 
 * @author german.kondolf
 * 
 * @param <T> concrete {@link Data} type.
 */
public abstract class AbstractGenericEvent<T extends Data>
    implements EventHandler {

    /**
     * Delegate templated execution to {@link EventHandler#execute(Data)}.
     * 
     * @param data {@link Data} as T
     * @return
     */
    protected abstract RoutingOutcome exec(T data);

    @SuppressWarnings("unchecked")
    public final RoutingOutcome execute(Data data) {
        // TODO: perhaps add here a type validation to avoid a ClassCastException?
        return this.exec((T) data);
    }

}
