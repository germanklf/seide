package org.seideframework.message;

import java.util.Collection;

import org.seideframework.event.Event;
import org.seideframework.support.Beta;


/**
 * This extension of {@link Message} represents multiple source {@link Event}s.
 * 
 * @author german.kondolf
 */
@Beta
public interface EventCollection
    extends Message {

    /**
     * @return the collected {@link Event}s.
     */
    Collection<Event> getEvents();

}
