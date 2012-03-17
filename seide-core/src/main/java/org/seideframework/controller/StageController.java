package org.seideframework.controller;

import org.seideframework.core.DispatcherAware;
import org.seideframework.core.Lifecycle;
import org.seideframework.core.RuntimeStage;
import org.seideframework.event.Event;
import org.seideframework.message.Message;
import org.seideframework.stages.Stage;

/**
 * The {@link StageController} is the responsible for the execution of the given {@link Event}, in this case, only the
 * {@link Message} because there is an instance of this class per defined {@link Stage}.
 * 
 * @author german.kondolf
 */
public interface StageController
    extends DispatcherAware, Lifecycle {

    /**
     * Execution handler for the given stage.
     * 
     * @param event
     */
    void execute(Event event);

    /**
     * Runtime configuration of the running {@link Stage} ({@link RuntimeStage}).
     * 
     * @param runtimeStage
     */
    void setRuntimeStage(RuntimeStage runtimeStage);

}
