package net.sf.seide.core;

import net.sf.seide.stages.Data;

public interface Dispatcher {

    void execute(String stage, Data data);

    void shutdown();

}
