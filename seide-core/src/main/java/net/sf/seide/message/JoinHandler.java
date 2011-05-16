package net.sf.seide.message;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.event.Event;
import net.sf.seide.event.EventHandler;
import net.sf.seide.support.Beta;
import net.sf.seide.support.Internal;

import org.apache.log4j.Logger;

@Beta
@Internal
public class JoinHandler {

    private static final Logger LOGGER = Logger.getLogger(JoinHandler.class);

    private final Dispatcher dispatcher;
    private final Event targetEvent;
    private final AtomicLong expectedEvents = new AtomicLong(0);
    private final AtomicLong finishedEvents = new AtomicLong(0);
    private final Collection<Event> collectedEvents;

    public JoinHandler(Dispatcher dispatcher, Event targetEvent) {
        this.dispatcher = dispatcher;
        this.targetEvent = targetEvent;
        this.collectedEvents = new LinkedList<Event>();
    }

    public Event getTargetEvent() {
        return this.targetEvent;
    }

    public void registerChild(EventHandler<?> source) {
        this.expectedEvents.incrementAndGet();
    }

    public void notifyChildOutcome(String stage, Message returnMessage) {
        if (returnMessage != null) {
            synchronized (this.collectedEvents) {
                this.collectedEvents.add(new Event(stage, returnMessage));
            }
        }

        long currentFinishedEvent = this.finishedEvents.incrementAndGet();
        long currentExpectedEvent = this.expectedEvents.get();

        if (currentFinishedEvent == currentExpectedEvent) {
            JoinEventCollection joinEventCollection = new JoinEventCollection(this.targetEvent.getMessage(),
                this.collectedEvents);
            Event joinEvent = new Event(this.targetEvent.getStage(), joinEventCollection);

            this.dispatcher.execute(joinEvent);
        }
    }
}
