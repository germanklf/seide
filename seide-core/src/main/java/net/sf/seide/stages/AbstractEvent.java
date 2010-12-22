package net.sf.seide.stages;


public abstract class AbstractEvent
    implements Event {

    protected Stage stage;

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
