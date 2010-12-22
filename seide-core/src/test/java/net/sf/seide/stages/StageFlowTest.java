package net.sf.seide.stages;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.sf.seide.core.impl.DispatcherImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
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
        this.dispatcher.execute("FIRST", new StageFirstEvent("FIRST-Value"));
    }

    /**
     * Basic multiple flow processing test
     * 
     * @throws Throwable
     */
    @Test
    public void testStraightMultipleFlow() throws Throwable {
        this.logger.info("START: testStraightMultipleFlow >>>>>>>>>>>>>>>>>>>>>>>");
        StageFirstEvent data = new StageFirstEvent("MULTI-FIRST-Value");
        data.setCount(10);
        this.dispatcher.execute("FIRST", data);
        // Thread.sleep(1000 * 130);
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
        for (int i = 0; i < iterations; i++) {
            StageFirstEvent data = new StageFirstEvent("MULTI-FIRST-Value");
            data.setCount(10);
            this.dispatcher.execute("FIRST", data);
        }
        Thread.sleep(1000 * 1);
    }

    @Before
    public void before() {
        this.dispatcher = new DispatcherImpl();
        List<Event> commands = new LinkedList<Event>();
        commands.add(this.createFirstStepCommand());
        commands.add(this.createSecondStepCommand());
        this.dispatcher.setEvents(commands);
        this.dispatcher.setContext("Test-" + Long.toHexString(UUID.randomUUID().toString().hashCode()));
        this.dispatcher.init();
    }

    @After
    public void after() throws Exception {
        this.dispatcher.shutdown();
    }

    private Event createFirstStepCommand() {
        Event command = new Event() {
            public Stage getStage() {
                Stage stage = new Stage();
                stage.setContext("TEST");
                stage.setId("FIRST");
                return stage;
            }

            public RoutingOutcome execute(Data data) {
                StageFirstEvent sfe = (StageFirstEvent) data;
                StageFlowTest.this.logger.info(">> First Stage Command: " + sfe.getValue());
                RoutingOutcome output = RoutingOutcome.create();
                for (int i = 0; i < sfe.getCount(); i++) {
                    output.add("SECOND", new StageSecondEvent("SECOND-Value:" + i));
                }
                return output;
                // return RoutingOutcome.create(, new StageSecondEvent("SECOND-Value"));
            }
        };
        return command;
    }

    private Event createSecondStepCommand() {
        Event command = new Event() {
            public Stage getStage() {
                Stage stage = new Stage();
                stage.setContext("TEST");
                stage.setId("SECOND");
                stage.setCoreThreads(100);
                stage.setMaxThreads(100);
                return stage;
            }

            public RoutingOutcome execute(Data data) {
                StageFlowTest.this.logger.info(">> Second Stage Command: " + ((StageSecondEvent) data).getValue());
                return null;
            }
        };
        return command;
    }

    private static class StageFirstEvent
        implements Data {
        private final String value;
        private int count = 1;

        public StageFirstEvent(String value) {
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

    private static class StageSecondEvent
        implements Data {
        private final String value;

        public StageSecondEvent(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
