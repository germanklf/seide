package org.seideframework.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Returns a {@link Thread} with the specified {@link Thread#getName()} and {@link Thread#getPriority()} configured.<br/>
 * Also concatenates to the name a suffix auto-incremented number.
 * 
 * @author german.kondolf
 */
public class DefaultThreadFactory
    implements ThreadFactory {

    private final AtomicLong threadNumber = new AtomicLong(0);

    private String name;
    private int priority = Thread.NORM_PRIORITY;

    public DefaultThreadFactory(String name) {
        this(name, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, this.name + "_#" + this.threadNumber.incrementAndGet());
        thread.setPriority(this.priority);
        return thread;
    }

}
