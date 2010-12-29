package net.sf.seide.thread;

import java.util.concurrent.ThreadPoolExecutor;

import javax.management.MXBean;

/**
 * Interface to expose the internal TPE values on JMX. <br/>
 * Nice idea took from Apache Cassandra's internal code.
 * 
 * @see {@link MXBean}
 * @see {@link ThreadPoolExecutor}
 * @author german.kondolf
 */
@MXBean
public interface JMXEnabledThreadPoolExecutor {

    /**
     * Get the current number of running tasks
     */
    public int getActiveCount();

    /**
     * Get the number of completed tasks
     */
    public long getCompletedTasks();

    /**
     * Get the number of tasks waiting to be executed
     */
    public long getPendingTasks();

}
