package net.sf.seide.stages;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import net.sf.seide.core.RuntimeStage;
import net.sf.seide.core.impl.DispatcherImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

@RunWith(BlockJUnit4ClassRunner.class)
public class LoadSheddingTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DispatcherImpl dispatcher;

    private CountDownLatch deterministicLatch;
    private CountDownLatch startAssertsLatch;

    @SuppressWarnings("unchecked")
    @Test
    public void testLoadShedding() throws Throwable {
        // 2 running...
        this.dispatcher.execute("OVERLOAD", null);
        this.dispatcher.execute("OVERLOAD", null);
        // 2 max waiting...
        this.dispatcher.execute("OVERLOAD", null);
        this.dispatcher.execute("OVERLOAD", null);
        // 2 discarded...
        this.dispatcher.execute("OVERLOAD", null);
        this.dispatcher.execute("OVERLOAD", null);

        this.startAssertsLatch.await();

        Field internalMapField = ReflectionUtils.findField(DispatcherImpl.class, "stagesMap");
        Assert.assertNotNull(internalMapField);
        internalMapField.setAccessible(true);
        Map<String, RuntimeStage> stagesMap = (Map<String, RuntimeStage>) ReflectionUtils.getField(internalMapField,
            this.dispatcher);
        Assert.assertNotNull(stagesMap);

        RuntimeStage runtimeStage = stagesMap.get("OVERLOAD");
        Assert.assertNotNull(runtimeStage);

        Assert.assertEquals(2, runtimeStage.getStageStats().getDiscardedExecutions());

        this.deterministicLatch.countDown();
    }

    @Before
    public void before() {
        this.deterministicLatch = new CountDownLatch(1);
        this.startAssertsLatch = new CountDownLatch(2);

        this.dispatcher = new DispatcherImpl();
        List<Stage> stages = new LinkedList<Stage>();
        stages.add(this.createStageEvent());
        this.dispatcher.setStages(stages);
        this.dispatcher.setContext("Test-" + Long.toHexString(UUID.randomUUID().toString().hashCode()));
        this.dispatcher.start();
    }

    @After
    public void after() {
        this.dispatcher.shutdown();
    }

    private Stage createStageEvent() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("OVERLOAD");
        stage.setMaxQueueSize(2);
        stage.setCoreThreads(2);
        stage.setMaxThreads(2);
        stage.setEventHandler(new AbstractGenericEvent<Data>() {
            @Override
            protected RoutingOutcome exec(Data data) {
                try {
                    LoadSheddingTest.this.startAssertsLatch.countDown();
                    LoadSheddingTest.this.deterministicLatch.await();
                } catch (InterruptedException e) {
                }
                return null;
            }
        });
        return stage;
    }

}
