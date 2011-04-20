package net.sf.seide.stages;

import java.util.LinkedList;
import java.util.List;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.event.Event;

/**
 * {@link RoutingOutcome} represents the output of an {@link EventHandler}, and instructs the {@link Dispatcher} the
 * routing of the upcoming {@link Event}s.
 * 
 * @author german.kondolf
 */
public class RoutingOutcome {

    private final List<Event> events;

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

    public static RoutingOutcome create(String stage, Data data) {
        RoutingOutcome routingOutcome = create();
        routingOutcome.add(stage, data);

        return routingOutcome;
    }

    public RoutingOutcome add(String stage, Data data) {
        return this.add(new Event(stage, data));
    }

    public RoutingOutcome add(Event event) {
        this.events.add(event);
        return this;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public boolean isEmpty() {
        return this.events.size() == 0;
    }

}
