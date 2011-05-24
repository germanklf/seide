package net.sf.seide.message;

import net.sf.seide.support.Beta;

/**
 * Adapts a {@link EventCollection} message subclass into a {@link Message}. Useful to transform resulting events into
 * the hub-join-event.
 * 
 * @author german.kondolf
 */
@Beta
public interface MessageAdapter<F extends EventCollection, T extends Message> {

    T adapt(F message);

}
