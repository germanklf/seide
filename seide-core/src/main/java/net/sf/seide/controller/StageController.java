package net.sf.seide.controller;

import net.sf.seide.core.DispatcherAware;
import net.sf.seide.core.Lifecycle;
import net.sf.seide.core.RuntimeStage;
import net.sf.seide.event.Event;
import net.sf.seide.stages.Data;
import net.sf.seide.stages.Stage;

/**
 * The {@link StageController} is the responsible for the execution of the given {@link Event}, in this case, only the
 * {@link Data} because there is an instance of this class per defined {@link Stage}.
 * 
 * @author german.kondolf
 */
public interface StageController
    extends DispatcherAware, Lifecycle {

    /**
     * Execution handler for the given stage.
     * 
     * @param data
     */
    void execute(Data data);

    /**
     * Runtime configuration of the running {@link Stage} ({@link RuntimeStage}).
     * 
     * @param runtimeStage
     */
    void setRuntimeStage(RuntimeStage runtimeStage);

}
