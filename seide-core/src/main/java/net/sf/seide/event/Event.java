package net.sf.seide.event;

import net.sf.seide.stages.Data;
import net.sf.seide.stages.Stage;

/**
 * The {@link EventHandler} represents the pair of {@link Stage} identifier and the proper {@link Data} associated for an
 * execution.
 * 
 * @author german.kondolf
 * @see {@link Data}
 * @see {@link Stage}
 */
public class Event {

    private final String stage;
    private final Data data;

    public Event(String stage, Data data) {
        this.stage = stage;
        this.data = data;
    }

    public String getStage() {
        return this.stage;
    }

    public Data getData() {
        return this.data;
    }

}
