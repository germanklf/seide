package net.sf.seide.controller;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.RuntimeStage;


public interface ThreadPoolController {

    void before(Dispatcher dispatcher, RuntimeStage context);

    void after(Dispatcher dispatcher, RuntimeStage context);

}
