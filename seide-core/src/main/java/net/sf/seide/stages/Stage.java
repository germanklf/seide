package net.sf.seide.stages;


public class Stage {

    private String context;
    private String id;
    private boolean monitoreable = true;
    private boolean loggeable = true;
    private int coreThreads = 5;
    private int maxThreads = 25;
    private int maxQueueSize = 0;

    private Event event;

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

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

}
