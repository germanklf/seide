package net.sf.seide.ext.spring3;

import net.sf.seide.core.Dispatcher;
import net.sf.seide.core.impl.DispatcherImpl;

public class DefaultDispatcherFactory {

    public static DispatcherFactory createDefault() {
        return new DispatcherFactory() {
            public Dispatcher create() {
                return new DispatcherImpl();
            }
        };
    }

}
