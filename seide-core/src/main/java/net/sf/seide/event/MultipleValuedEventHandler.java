package net.sf.seide.event;

import net.sf.seide.message.EventCollection;
import net.sf.seide.support.Beta;

@Beta
public interface MultipleValuedEventHandler<T extends EventCollection>
    extends EventHandler<T> {

}
