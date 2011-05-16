package net.sf.seide.message;

import java.util.Collection;

import net.sf.seide.event.Event;
import net.sf.seide.support.Beta;

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
