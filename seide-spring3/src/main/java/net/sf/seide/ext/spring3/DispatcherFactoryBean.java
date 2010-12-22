package net.sf.seide.ext.spring3;

import java.util.LinkedList;
import java.util.Map;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.stages.Stage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


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

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        this.dispatcher.shutdown();
    }

}
