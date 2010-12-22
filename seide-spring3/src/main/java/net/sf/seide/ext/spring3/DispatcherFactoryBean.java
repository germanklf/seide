package net.sf.seide.ext.spring3;

import java.util.LinkedList;
import java.util.Map;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.impl.DispatcherImpl;
import net.sf.seide.stages.Event;

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

    @Override
    public Dispatcher getObject() throws Exception {
        if (this.dispatcher != null) {
            return this.dispatcher;
        }

        LinkedList<Event> events = new LinkedList<Event>();

        Map<String, Event> beansOfType = this.applicationContext.getBeansOfType(Event.class, true, false);
        for (Event sc : beansOfType.values()) {
            if (sc.getStage().getContext().equalsIgnoreCase(this.context)) {
                events.add(sc);
            }
        }
        DispatcherImpl newDispatcher = new DispatcherImpl();
        newDispatcher.setEvents(events);
        newDispatcher.init();

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
