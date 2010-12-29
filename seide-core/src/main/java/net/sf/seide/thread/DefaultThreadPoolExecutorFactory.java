package net.sf.seide.thread;

import java.util.concurrent.ThreadPoolExecutor;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.stages.Stage;

/**
 * Default {@link ThreadPoolExecutorFactory} that creates a {@link DispatcherThreadPoolExecutor} configured according to
 * the specified {@link Stage} and {@link Dispatcher}.
 * 
 * @author german.kondolf
 */
public class DefaultThreadPoolExecutorFactory
    implements ThreadPoolExecutorFactory {

    public ThreadPoolExecutor create(Dispatcher dispatcher, Stage stage) {
        ThreadPoolExecutor executor = new DispatcherThreadPoolExecutor(dispatcher.getContext() + "_" + stage.getId()
            + "_ST#", stage.getCoreThreads(), stage.getMaxThreads());
        return executor;
    }

}
