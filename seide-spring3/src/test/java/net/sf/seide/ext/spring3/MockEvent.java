package net.sf.seide.ext.spring3;

import net.sf.seide.stages.Data;
import net.sf.seide.stages.EventHandler;
import net.sf.seide.stages.RoutingOutcome;

public class MockEvent
    implements EventHandler {

    @Override
    public RoutingOutcome execute(Data data) {
        return null;
    }

}
