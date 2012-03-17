package org.seideframework.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


import org.seideframework.core.RuntimeStage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadSheddingPolicy
    implements RejectedExecutionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoadSheddingPolicy.class);
    private RuntimeStage runtimeStage;

    public LoadSheddingPolicy(RuntimeStage runtimeStage) {
        this.runtimeStage = runtimeStage;
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        this.runtimeStage.getStageStats().trackDiscardedExecution();
        LOGGER.info("Discarded execution for stage [" + this.runtimeStage.getId() + "]...");
    }

    public void setStageContext(RuntimeStage runtimeStage) {
        this.runtimeStage = runtimeStage;
    }

}
