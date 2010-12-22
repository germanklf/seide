package net.sf.seide.stages;

import javax.management.MXBean;

@MXBean
public interface StageStatistics {

    String getContext();

    String getId();

    long getPendingCount();

    long getRunningCount();

    long getTotalExecutionCount();

    long getTotalExecutionTime();

    long getMinExecutionTime();

    double getAvgExecutionTime();

    long getMaxExecutionTime();

    void addPending();

    void removePending();

    void addRunning();

    void removeRunning();

    void trackTimeAndExecution(long time);

}
