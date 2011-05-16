package net.sf.seide.stages;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import net.sf.seide.core.impl.DispatcherImpl;
import net.sf.seide.event.Event;
import net.sf.seide.event.EventHandler;
import net.sf.seide.event.JoinEventHandler;
import net.sf.seide.message.JoinEventCollection;
import net.sf.seide.message.Message;
import net.sf.seide.support.Beta;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Beta
@RunWith(BlockJUnit4ClassRunner.class)
public class ForkAndJoinTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DispatcherImpl dispatcher;
    private CountDownLatch waitLatch;

    @Test
    public void testForkAndJoin() throws Throwable {
        this.dispatcher.execute("STARTING", new StartingMessage());
        this.waitLatch.await();
    }

    @Before
    public void before() {
        this.dispatcher = new DispatcherImpl();
        List<Stage> stages = new LinkedList<Stage>();
        stages.add(this.createStartingEH());
        stages.add(this.createAPathEH());
        stages.add(this.createBPathEH());
        stages.add(this.createJoinEH());
        this.dispatcher.setStages(stages);
        this.dispatcher.setContext("Test-" + Long.toHexString(UUID.randomUUID().toString().hashCode()));
        this.dispatcher.start();

        this.waitLatch = new CountDownLatch(1);
    }

    @After
    public void after() {
        this.dispatcher.stop();
    }

    private Stage createStartingEH() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("STARTING");
        stage.setEventHandler(new EventHandler<StartingMessage>() {
            public RoutingOutcome execute(StartingMessage message) {
                RoutingOutcome routingOutcome = RoutingOutcome.create();
                routingOutcome.add("APATH", new APathMessage("A"));
                routingOutcome.add("BPATH", new BPathMessage("B"));

                routingOutcome.configureJoinEvent(new Event("JOIN", new JoinMessage("Join: A+B")));

                return routingOutcome;
            }
        });
        return stage;
    }

    private Stage createAPathEH() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("APATH");
        stage.setEventHandler(new EventHandler<APathMessage>() {
            public RoutingOutcome execute(APathMessage message) {
                ForkAndJoinTest.this.logger.info("A: OK!");
                return RoutingOutcome.createAndReturnMessage(message);
            }
        });
        return stage;
    }

    private Stage createBPathEH() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("BPATH");
        stage.setEventHandler(new EventHandler<BPathMessage>() {
            public RoutingOutcome execute(BPathMessage message) {
                ForkAndJoinTest.this.logger.info("B: OK!");
                return RoutingOutcome.createAndReturnMessage(message);
            }
        });
        return stage;
    }

    private Stage createJoinEH() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("JOIN");
        stage.setEventHandler(new JoinEventHandler() {
            public RoutingOutcome execute(JoinEventCollection data) {
                JoinMessage jm = (JoinMessage) data.getTargetMessage();
                ForkAndJoinTest.this.logger.info("Join: OK!");
                ForkAndJoinTest.this.logger.info("JoinMessage: " + jm.value);

                for (Event e : data.getEvents()) {
                    ForkAndJoinTest.this.logger.info("From '" + e.getStage() + "', received:" + e.getMessage());
                }
                ForkAndJoinTest.this.waitLatch.countDown();
                return null;
            }
        });
        return stage;
    }

    class StartingMessage
        implements Message {
    }

    class APathMessage
        implements Message {

        public final String value;

        public APathMessage(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    class BPathMessage
        implements Message {
        public final String value;

        public BPathMessage(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
    class JoinMessage
        implements Message {
        public final String value;

        public JoinMessage(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}
