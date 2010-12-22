package net.sf.seide.core;

import net.sf.seide.stages.Event;

/**
 * Declares {@link Dispatcher} awareness for an {@link Event} mainly.
 * 
 * @author german.kondolf
 */
public interface DispatcherAware {

    Dispatcher getDispatcher();

    void setDispatcher(Dispatcher dispatcher);

}
