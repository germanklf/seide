package net.sf.seide.stages;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import net.sf.seide.core.StageContext;
import net.sf.seide.core.impl.DispatcherImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

@Ignore
@RunWith(BlockJUnit4ClassRunner.class)
public class LoadSheddingTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DispatcherImpl dispatcher;

    private CountDownLatch deterministicLatch;
    private CountDownLatch startAssertsLatch;

    @SuppressWarnings("unchecked")
    @Test
    public void testLoadShedding() throws Throwable {
        this.dispatcher.execute("OVERLOAD", null);
        this.dispatcher.execute("OVERLOAD", null);
        this.dispatcher.execute("OVERLOAD", null);
        this.dispatcher.execute("OVERLOAD", null);

        this.startAssertsLatch.await();

        Field internalMapField = ReflectionUtils.findField(DispatcherImpl.class, "stagesMap");
        Assert.assertNotNull(internalMapField);
        internalMapField.setAccessible(true);
        Map<String, StageContext> stagesMap = (Map<String, StageContext>) ReflectionUtils.getField(internalMapField,
            this.dispatcher);
        Assert.assertNotNull(stagesMap);

        StageContext stageContext = stagesMap.get("OVERLOAD");
        Assert.assertNotNull(stageContext);

        Assert.assertEquals(2, stageContext.getStageStats().getDiscardedExecutions());

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

    private Stage createStageEvent() {
        Stage stage = new Stage();
        stage.setContext("TEST");
        stage.setId("OVERLOAD");
        stage.setMaxQueueSize(2);
        stage.setCoreThreads(10);
        stage.setMaxThreads(10);
        stage.setEvent(new AbstractGenericEvent<Data>() {
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
