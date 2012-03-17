package org.seideframework.ext.spring3;

import org.seideframework.core.Dispatcher;
import org.seideframework.core.impl.DispatcherImpl;

public class DefaultDispatcherFactory {

    public static DispatcherFactory createDefault() {
        return new DispatcherFactory() {
            public Dispatcher create() {
                return new DispatcherImpl();
            }
        };
    }

}
