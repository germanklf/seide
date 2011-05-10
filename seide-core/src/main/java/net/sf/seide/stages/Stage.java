package net.sf.seide.stages;

import net.sf.seide.controller.StageController;
import net.sf.seide.controller.impl.DirectThreadPoolStageController;


public class Stage {

    private String context;
    private String id;
    private boolean monitoreable = true;
    private boolean loggeable = true;
    private int coreThreads = 5;
    private int maxThreads = 25;
    private int maxQueueSize = 0;

    private EventHandler eventHandler;
    private StageController controller = new DirectThreadPoolStageController();

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public boolean isMonitoreable() {
        return this.monitoreable;
    }

    public void setMonitoreable(boolean monitoreable) {
        this.monitoreable = monitoreable;
    }

    public boolean isLoggeable() {
        return this.loggeable;
    }

    public void setLoggeable(boolean loggeable) {
        this.loggeable = loggeable;
    }

    public int getCoreThreads() {
        return this.coreThreads;
    }

    public void setCoreThreads(int coreThreads) {
        this.coreThreads = coreThreads;
    }

    public int getMaxThreads() {
        return this.maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxQueueSize() {
        return this.maxQueueSize;
    }

    public void setMaxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public StageController getController() {
        return this.controller;
    }

    public void setController(StageController controller) {
        this.controller = controller;
    }

}
