package org.seideframework.thread;

import java.util.concurrent.ThreadPoolExecutor;

import javax.management.MXBean;

/**
 * Interface to allow control of the TPE over JMX.<br/>
 * Nice idea took from Apache Cassandra's internal code.
 * 
 * @see {@link MXBean}
 * @see {@link ThreadPoolExecutor}
 * @author german.kondolf
 */
@MXBean
public interface JMXConfigurableThreadPoolExecutor
    extends JMXEnabledThreadPoolExecutor {

    /**
     * Sets the core pool size to the TPE.
     * 
     * @param n number of core threads.
     */
    void setCorePoolSize(int n);

    /**
     * @return current core pool size.
     */
    int getCorePoolSize();

}
