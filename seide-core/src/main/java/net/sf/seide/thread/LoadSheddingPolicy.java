package net.sf.seide.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import net.sf.seide.core.StageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadSheddingPolicy
    implements RejectedExecutionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoadSheddingPolicy.class);
    private StageContext stageContext;

    public LoadSheddingPolicy(StageContext stageContext) {
        this.stageContext = stageContext;
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        this.stageContext.getStageStats().trackDiscardedExecution();
        LOGGER.debug("Discarded execution...");
    }

    public void setStageContext(StageContext stageContext) {
        this.stageContext = stageContext;
    }

}
