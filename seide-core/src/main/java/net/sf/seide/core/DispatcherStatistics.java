package net.sf.seide.core;

import javax.management.MXBean;

@MXBean
public interface DispatcherStatistics {

    public int getStageCount();

    public long getTotalEventExecutionsCount();

}
