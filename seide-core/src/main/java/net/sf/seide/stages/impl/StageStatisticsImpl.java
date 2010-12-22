package net.sf.seide.stages.impl;

import java.util.concurrent.atomic.AtomicLong;

import net.sf.seide.stages.StageStatistics;

public class StageStatisticsImpl
    implements StageStatistics {

    private final String context;
    private final String id;
    private final AtomicLong pending = new AtomicLong(0);
    private final AtomicLong running = new AtomicLong(0);
    private final AtomicLong totalExecutions = new AtomicLong(0);
    private final AtomicLong totalExecutionTime = new AtomicLong(0);
    private final AtomicLong minExecutionTime = new AtomicLong(-1);
    private final AtomicLong maxExecutionTime = new AtomicLong(-1);

    public StageStatisticsImpl(String context, String id) {
        this.context = context;
        this.id = id;
    }

    public String getContext() {
        return this.context;
    }

    public String getId() {
        return this.id;
    }

    public long getPendingCount() {
        return this.pending.get();
    }

    public long getRunningCount() {
        return this.running.get();
    }

    public long getTotalExecutionCount() {
        return this.totalExecutions.get();
    }

    public long getTotalExecutionTime() {
        return this.totalExecutionTime.get();
    }

    public long getMinExecutionTime() {
        return this.minExecutionTime.get();
    }

    public long getMaxExecutionTime() {
        return this.maxExecutionTime.get();
    }

    public double getAvgExecutionTime() {
        long count = this.totalExecutions.get();
        if (count > 0) {
            return this.totalExecutionTime.get() / count * 1.0;
        } else {
            return 0;
        }
    }

    public void addPending() {
        this.pending.incrementAndGet();
    }

    public void removePending() {
        this.pending.decrementAndGet();
    }

    public void addRunning() {
        this.running.incrementAndGet();
    }

    public void removeRunning() {
        this.running.decrementAndGet();
    }

    public void trackTimeAndExecution(long time) {
        this.totalExecutions.incrementAndGet();
        this.totalExecutionTime.addAndGet(time);

        // FIXME: buggy... someone could change the value while I'm writting...
        if (!this.minExecutionTime.compareAndSet(-1, time)) {
            long safe = this.minExecutionTime.get();
            if (safe < time) {
                this.minExecutionTime.compareAndSet(safe, time);
            }
        }
        if (!this.maxExecutionTime.compareAndSet(-1, time)) {
            long safe = this.maxExecutionTime.get();
            if (safe > time) {
                this.maxExecutionTime.compareAndSet(safe, time);
            }
        }
    }

}
