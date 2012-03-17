package org.seideframework.ext.spring3;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.seideframework.core.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:org/seideframework/ext/spring3/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ContextLoadTest {

    @Autowired
    private Dispatcher dispatcher;

    @Test
    public void testContextLoading() {
    }

}
