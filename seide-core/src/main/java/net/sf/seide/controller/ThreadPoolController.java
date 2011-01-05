package net.sf.seide.controller;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.StageContext;


public interface ThreadPoolController {

    void before(Dispatcher dispatcher, StageContext context);

    void after(Dispatcher dispatcher, StageContext context);

}
