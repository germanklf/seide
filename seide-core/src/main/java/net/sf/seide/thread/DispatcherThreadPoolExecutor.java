package net.sf.seide.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.sf.seide.core.Dispatcher;

/**
 * {@link Dispatcher} friendly {@link ThreadPoolExecutor} subclass to provide JMX access to internal values and allows
 * to configure the
 * 
 * 
 * @see {@link ThreadPoolExecutor}
 * @author german.kondolf
 */
public class DispatcherThreadPoolExecutor
    extends ThreadPoolExecutor
    implements JMXEnabledThreadPoolExecutor, JMXConfigurableThreadPoolExecutor {

    private static final int CORE_THREADS_TIMEOUT = 120;

    public DispatcherThreadPoolExecutor(String namePrefix) {
        this(1, 1, CORE_THREADS_TIMEOUT, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(
            namePrefix, Thread.NORM_PRIORITY), new DiscardPolicy());
    }

    public DispatcherThreadPoolExecutor(String namePrefix, int corePoolSize, int maxPoolSize) {
        this(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new DefaultThreadFactory(namePrefix, Thread.NORM_PRIORITY), new DiscardPolicy());
    }

    public DispatcherThreadPoolExecutor(String namePrefix, int corePoolSize, int maximumPoolSize, int maxQueueSize,
        RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, CORE_THREADS_TIMEOUT, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(
            maxQueueSize), new DefaultThreadFactory(namePrefix, Thread.NORM_PRIORITY), handler);
    }

    public DispatcherThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public long getCompletedTasks() {
        return this.getCompletedTaskCount();
    }

    @Override
    public long getPendingTasks() {
        return this.getQueue().size();
    }

}
