package net.sf.seide.message;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import net.sf.seide.event.Event;
import net.sf.seide.support.Beta;
import net.sf.seide.support.Internal;

@Beta
@Internal
public class JoinEventCollection
    implements EventCollection {

    protected final Collection<Event> events;
    protected final Message targetMessage;

    public JoinEventCollection(Message targetMessage, Collection<Event> events) {
        if (events != null && !events.isEmpty()) {
            this.events = Collections.unmodifiableCollection(new LinkedList<Event>(events));
        } else {
            this.events = null;
        }
        this.targetMessage = targetMessage;
    }

    public Collection<Event> getEvents() {
        return this.events;
    }

    public Message getTargetMessage() {
        return this.targetMessage;
    }

}
