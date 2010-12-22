package net.sf.seide.core;

import javax.management.MXBean;

/**
 * {@link Dispatcher}'s internal statistics, under the hood it will use JMX to expose them.
 * 
 * @author german.kondolf
 */
@MXBean
public interface DispatcherStatistics {

    /**
     * @return # of stages defined in the given dispatcher.
     */
    public int getStageCount();

    /**
     * @return # of total executions since the {@link Dispatcher#start()} call.
     */
    public long getTotalEventExecutionsCount();

}
