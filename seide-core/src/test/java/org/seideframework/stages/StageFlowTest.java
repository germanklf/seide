package org.seideframework.stages;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.seideframework.core.impl.DispatcherImpl;
import org.seideframework.event.EventHandler;
import org.seideframework.message.Message;
import org.seideframework.stages.RoutingOutcome;
import org.seideframework.stages.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(BlockJUnit4ClassRunner.class)
public class StageFlowTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DispatcherImpl dispatcher;

    /**
     * Basic flow processing test
     */
    @Test
    public void testStraightFlow() {
        this.logger.info("START: testStraightFlow >>>>>>>>>>>>>>>>>>>>>>>");
        this.dispatcher.execute("FIRST", new StageFirstMessage("FIRST-Message"));
    }

    /**
     * Basic multiple flow processing test
     * 
     * @throws Throwable
     */
    @Test
    public void testStraightMultipleFlow() throws Throwable {
        this.logger.info("START: testStraightMultipleFlow >>>>>>>>>>>>>>>>>>>>>>>");
        StageFirstMessage data = new StageFirstMessage("MULTI-FIRST-Message");
        data.setCount(10);
        this.dispatcher.execute("FIRST", data);
    }

    /**
     * Heavy multiple flow processing test
     * 
     * @throws Throwable
     */
    @Test
    public void testHeavyStraightMultipleFlow() throws Throwable {
        this.logger.info("START: testStraightMultipleFlow >>>>>>>>>>>>>>>>>>>>>>>");
        int iterations = 200;
        // int iterations = 200000;
        for (int i = 0; i < iterations; i++) {
            StageFirstMessage data = new StageFirstMessage("MULTI-FIRST-Message");
            data.setCount(10);
            this.dispatcher.execute("FIRST", data);
        }
        Thread.sleep(1000 * 1);
        // Thread.sleep(1000 * 130);
    }

    @Before
    public void before() {
        this.dispatcher = new DispatcherImpl();
        List<Stage> stages = new LinkedList<Stage>();
        stages.add(this.createFirstStepMessage());
        stages.add(this.createSecondStepMessage());
        this.dispatcher.setStages(stages);
        this.dispatcher.setContext("Test-" + Long.toHexString(UUID.randomUUID().toString().hashCode()));
        this.dispatcher.start();
    }

    @After
    public void after() throws Exception {
        this.dispatcher.stop();
    }

    private Stage createFirstStepMessage() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("FIRST");
        stage.setEventHandler(new EventHandler<StageFirstMessage>() {
            public RoutingOutcome execute(StageFirstMessage data) {
                StageFirstMessage sfe = data;
                StageFlowTest.this.logger.info(">> First Stage Command: " + sfe.getValue());
                RoutingOutcome output = RoutingOutcome.create();
                for (int i = 0; i < sfe.getCount(); i++) {
                    output.add("SECOND", new StageSecondMessage("SECOND-Message:" + i));
                }
                return output;
            }
        });
        return stage;
    }

    private Stage createSecondStepMessage() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("SECOND");
        stage.setCoreThreads(100);
        stage.setMaxThreads(100);
        stage.setEventHandler(new EventHandler<StageSecondMessage>() {
            public RoutingOutcome execute(StageSecondMessage data) {
                StageFlowTest.this.logger.info(">> Second Stage Command: " + (data).getValue());
                return null;
            }
        });
        return stage;
    }

    private static class StageFirstMessage
        implements Message {
        private final String value;
        private int count = 1;

        public StageFirstMessage(String value) {
            this.value = value;
        }

        public int getCount() {
            return this.count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getValue() {
            return this.value;
        }
    }

    private static class StageSecondMessage
        implements Message {
        private final String value;

        public StageSecondMessage(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
