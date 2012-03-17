package org.seideframework.ext.spring3;

import java.util.LinkedList;
import java.util.Map;


import org.seideframework.core.Dispatcher;
import org.seideframework.stages.Stage;
import org.seideframework.thread.ThreadPoolExecutorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * A {@link FactoryBean} that creates a {@link Dispatcher} configured with the {@link ThreadPoolExecutorFactory} and
 * exploring the context to retrieve automatically the {@link Stage}s with the same {@link #context} that this bean.
 * 
 * @see {@link Dispatcher}
 * @see {@link DispatcherFactory}
 * @see {@link ThreadPoolExecutorFactory}
 * @author german.kondolf
 */
public class DispatcherFactoryBean
    implements FactoryBean<Dispatcher>, ApplicationContextAware, DisposableBean {

    private ApplicationContext applicationContext;
    private String context;
    private Dispatcher dispatcher;

    private DispatcherFactory dispatcherFactory = DefaultDispatcherFactory.createDefault();

    @Override
    public Dispatcher getObject() throws Exception {
        if (this.dispatcher != null) {
            return this.dispatcher;
        }

        LinkedList<Stage> stages = new LinkedList<Stage>();

        Map<String, Stage> beansOfType = this.applicationContext.getBeansOfType(Stage.class, true, false);
        for (Stage stage : beansOfType.values()) {
            if (stage.getContext().trim().equalsIgnoreCase(this.context)) {
                stages.add(stage);
            }
        }
        Dispatcher newDispatcher = this.dispatcherFactory.create();
        newDispatcher.setStages(stages);
        newDispatcher.setContext(this.context);
        newDispatcher.start();

        this.dispatcher = newDispatcher;

        return this.dispatcher;
    }

    @Override
    public Class<?> getObjectType() {
        return Dispatcher.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getContext() {
        return this.context;
    }

    @Required
    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        this.dispatcher.stop();
    }

}
