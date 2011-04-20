package net.sf.seide.thread;

import java.util.concurrent.ThreadPoolExecutor;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.RuntimeStage;
import net.sf.seide.stages.Stage;

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
