package org.seideframework.core;

import org.seideframework.event.EventHandler;

/**
 * Declares {@link Dispatcher} awareness for an {@link EventHandler} mainly.
 * 
 * @author german.kondolf
 */
public interface DispatcherAware {

    Dispatcher getDispatcher();

    void setDispatcher(Dispatcher dispatcher);

}
