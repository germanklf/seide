package org.seideframework.stages;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.seideframework.core.Dispatcher;
import org.seideframework.event.Event;
import org.seideframework.event.EventHandler;
import org.seideframework.message.Message;


/**
 * {@link RoutingOutcome} represents the output of an {@link EventHandler}, and instructs the {@link Dispatcher} the
 * routing of the upcoming {@link Event}s.
 * 
 * @author german.kondolf
 */
public class RoutingOutcome {

    private final Collection<Event> events;
    private Event joinEvent = null;
    private Message returnMessage = null;

    public RoutingOutcome() {
        // linked list to use the same order that the user specified in the adding process.
        this.events = new LinkedList<Event>();
    }

    public static RoutingOutcome create() {
        return new RoutingOutcome();
    }

    public static RoutingOutcome create(Event event) {
        RoutingOutcome routingOutcome = create();
        routingOutcome.add(event);

        return routingOutcome;
    }

    public static RoutingOutcome create(String stage, Message message) {
        RoutingOutcome routingOutcome = create();
        routingOutcome.add(stage, message);

        return routingOutcome;
    }

    public static RoutingOutcome createAndReturnMessage(Message returnMessage) {
        RoutingOutcome routingOutcome = create();
        routingOutcome.returnMessage(returnMessage);

        return routingOutcome;
    }

    public RoutingOutcome add(String stage, Message message) {
        return this.add(new Event(stage, message));
    }

    public RoutingOutcome add(Event event) {
        this.events.add(event);
        return this;
    }

    public RoutingOutcome configureJoinEvent(Event event) {
        assert !this.hasJoinEvent() : "join event already set";
        this.joinEvent = event;

        return this;
    }

    public RoutingOutcome returnMessage(Message returnMessage) {
        this.returnMessage = returnMessage;
        return this;
    }

    public Message getReturnMessage() {
        return this.returnMessage;
    }

    public Collection<Event> getEvents() {
        return Collections.unmodifiableCollection(this.events);
    }

    public boolean hasJoinEvent() {
        return this.joinEvent != null;
    }

    public Event getJoinEvent() {
        return this.joinEvent;
    }

    public boolean isEmpty() {
        return this.events.size() == 0;
    }

}
