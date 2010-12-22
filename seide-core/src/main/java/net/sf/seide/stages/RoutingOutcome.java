package net.sf.seide.stages;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoutingOutcome {

    private final Map<String, List<Data>> output;

    public RoutingOutcome() {
        // linked hash-map to use the same order that the user specified in the adding process.
        this.output = new LinkedHashMap<String, List<Data>>();
    }

    public static RoutingOutcome create() {
        return new RoutingOutcome();
    }

    public static RoutingOutcome create(String stage, Data data) {
        RoutingOutcome routingOutcome = create();
        routingOutcome.add(stage, data);

        return routingOutcome;
    }

    public void add(String stage, Data data) {
        List<Data> events = this.output.get(stage);
        if (events == null) {
            events = new LinkedList<Data>();
            this.output.put(stage, events);
        }
        events.add(data);
    }

    public Map<String, List<Data>> getOutput() {
        return this.output;
    }

    public boolean isEmpty() {
        return this.output.size() == 0;
    }

}
