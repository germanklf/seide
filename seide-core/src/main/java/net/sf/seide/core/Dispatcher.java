package net.sf.seide.core;

import java.util.List;

import net.sf.seide.stages.Data;
import net.sf.seide.stages.Stage;

public interface Dispatcher {

    void start();

    void execute(String stage, Data data);

    void shutdown();

    void setStages(List<Stage> stages);

}
