package org.seideframework.thread;

import java.util.concurrent.ThreadPoolExecutor;

import org.seideframework.core.Dispatcher;
import org.seideframework.core.RuntimeStage;
import org.seideframework.stages.Stage;


/**
 * Creates a custom {@link ThreadPoolExecutor} for a given {@link Stage} and contextual {@link Dispatcher}.
 * 
 * @author german.kondolf
 */
public interface ThreadPoolExecutorFactory {

    ThreadPoolExecutor create(Dispatcher dispatcher, RuntimeStage runtimeStage);

}
