package net.sf.seide.core;

import net.sf.seide.event.EventHandler;

/**
 * Declares {@link Dispatcher} awareness for an {@link EventHandler} mainly.
 * 
 * @author german.kondolf
 */
public interface DispatcherAware {

    Dispatcher getDispatcher();

    void setDispatcher(Dispatcher dispatcher);

}
