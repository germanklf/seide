package org.seideframework.thread;

import java.util.concurrent.ThreadPoolExecutor;

import org.seideframework.core.Dispatcher;
import org.seideframework.core.RuntimeStage;
import org.seideframework.stages.Stage;


/**
 * Default {@link ThreadPoolExecutorFactory} that creates a {@link DispatcherThreadPoolExecutor} configured according to
 * the specified {@link Stage} and {@link Dispatcher}.
 * 
 * @author german.kondolf
 */
public class DefaultThreadPoolExecutorFactory
    implements ThreadPoolExecutorFactory {

    public ThreadPoolExecutor create(Dispatcher dispatcher, RuntimeStage runtimeStage) {
        Stage stage = runtimeStage.getStage();
        ThreadPoolExecutor executor;
        if (stage.getMaxQueueSize() > 0) {
            executor = new DispatcherThreadPoolExecutor(dispatcher.getContext() + "_" + stage.getId() + "_ST#",
                stage.getCoreThreads(), stage.getMaxThreads(), stage.getMaxQueueSize(), new LoadSheddingPolicy(runtimeStage));
        } else {
            executor = new DispatcherThreadPoolExecutor(dispatcher.getContext() + "_" + stage.getId() + "_ST#",
                stage.getCoreThreads(), stage.getMaxThreads());
        }
        return executor;
    }
}
