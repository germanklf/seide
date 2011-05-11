package net.sf.seide.stages;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.seide.controller.impl.DirectThreadPoolStageController;
import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.impl.DispatcherImpl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(BlockJUnit4ClassRunner.class)
public class TimeoutHandlingTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Dispatcher dispatcher;
    private AtomicBoolean finishedOK = new AtomicBoolean(false);
    private AtomicBoolean interrupted = new AtomicBoolean(false);

    @Test
    public void testTimeoutNotReached() throws InterruptedException {
        this.logger.info("testTimeoutNotReached >>");
        this.configure(10);

        this.dispatcher.execute("TIMEOUT-STAGE", null);
        Thread.sleep(250);

        Assert.assertTrue(this.finishedOK.get());
        Assert.assertFalse(this.interrupted.get());

        this.stop();
    }

    @Test
    public void testTimeoutReached() throws InterruptedException {
        this.logger.info("testTimeoutReached >>");
        this.configure(500);

        this.dispatcher.execute("TIMEOUT-STAGE", null);
        Thread.sleep(250);

        Assert.assertFalse(this.finishedOK.get());
        Assert.assertTrue(this.interrupted.get());

        this.stop();
    }

    private void configure(int waitTime) {
        this.dispatcher = new DispatcherImpl();
        List<Stage> stages = new LinkedList<Stage>();
        stages.add(this.createTimeoutStage(waitTime));
        this.dispatcher.setStages(stages);
        this.dispatcher.setContext("Test-" + Long.toHexString(UUID.randomUUID().toString().hashCode()));
        this.dispatcher.start();
        this.finishedOK.set(false);
        this.interrupted.set(false);
    }

    private Stage createTimeoutStage(final int waitTime) {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("TIMEOUT-STAGE");
        DirectThreadPoolStageController controller = new DirectThreadPoolStageController();
        controller.setTimeout(200);
        stage.setController(controller);
        stage.setEventHandler(new EventHandler() {
            public RoutingOutcome execute(Data data) {
                TimeoutHandlingTest.this.logger.info("Waiting...");
                try {
                    Thread.sleep(waitTime);
                    TimeoutHandlingTest.this.finishedOK.set(true);
                    TimeoutHandlingTest.this.logger.info("Finished OK!");
                } catch (InterruptedException e) {
                    TimeoutHandlingTest.this.logger.info("Timeout reached!");
                    TimeoutHandlingTest.this.interrupted.set(true);
                }
                return null;
            }
        });
        return stage;
    }

    private void stop() {
        this.dispatcher.stop();
    }

}
