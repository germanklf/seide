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

    public DispatcherThreadPoolExecutor(String namePrefix) {
        this(1, 1, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(
            namePrefix, Thread.NORM_PRIORITY), new DiscardPolicy());
    }

    public DispatcherThreadPoolExecutor(String namePrefix, int corePoolSize) {
        this(corePoolSize, corePoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new DefaultThreadFactory(namePrefix, Thread.NORM_PRIORITY), new DiscardPolicy());
    }

    public DispatcherThreadPoolExecutor(String namePrefix, int corePoolSize, int maxPoolSize) {
        this(corePoolSize, maxPoolSize, Integer.MAX_VALUE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new DefaultThreadFactory(namePrefix, Thread.NORM_PRIORITY), new DiscardPolicy());
    }

    public DispatcherThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public long getCompletedTasks() {
        return super.getCompletedTaskCount();
    }

    @Override
    public long getPendingTasks() {
        return super.getQueue().size();
    }

}
