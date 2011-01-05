package net.sf.seide.thread;

import java.util.concurrent.ThreadPoolExecutor;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.StageContext;
import net.sf.seide.stages.Stage;

/**
 * Creates a custom {@link ThreadPoolExecutor} for a given {@link Stage} and contextual {@link Dispatcher}.
 * 
 * @author german.kondolf
 */
public interface ThreadPoolExecutorFactory {

    ThreadPoolExecutor create(Dispatcher dispatcher, StageContext stageContext);

}
