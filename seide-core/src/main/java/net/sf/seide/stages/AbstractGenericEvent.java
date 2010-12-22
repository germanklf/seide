package net.sf.seide.stages;

public abstract class AbstractGenericEvent<T extends Data>
    implements Event {

    protected abstract RoutingOutcome exec(T data);

    @SuppressWarnings("unchecked")
    public final RoutingOutcome execute(Data data) {
        // TODO: perhaps add here a type validation to avoid a ClassCastException?
        return this.exec((T) data);
    }

}
