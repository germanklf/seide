package net.sf.seide.message;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import net.sf.seide.event.Event;
import net.sf.seide.support.Beta;

@Beta
public class JoinEventCollection
    implements EventCollection, MessageEnabled {

    protected final Collection<Event> events;
    protected final Message message;

    public JoinEventCollection(Message message) {
        this.events = new LinkedList<Event>();
        this.message = message;
    }

    public Collection<Event> getEvents() {
        return Collections.unmodifiableCollection(this.events);
    }

    public Message getMessage() {
        return this.message;
    }

}
