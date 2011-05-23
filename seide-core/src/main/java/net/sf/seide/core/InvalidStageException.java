package net.sf.seide.core;

public class InvalidStageException
    extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String stage;

    public InvalidStageException(String stage) {
        this(stage, "n/a", null);
    }

    public InvalidStageException(String stage, String message) {
        this(stage, message, null);
    }

    public InvalidStageException(String stage, Throwable cause) {
        this(stage, "n/a", cause);
    }

    public InvalidStageException(String stage, String message, Throwable cause) {
        super("Invalid stage: " + stage + " - Detailed message: " + message, cause);
        this.stage = stage;
    }

    public String getStage() {
        return this.stage;
    }

}
