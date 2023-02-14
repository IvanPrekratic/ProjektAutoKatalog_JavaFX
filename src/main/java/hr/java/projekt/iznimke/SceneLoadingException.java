package hr.java.projekt.iznimke;

public class SceneLoadingException extends Exception{
    public SceneLoadingException() {
    }

    public SceneLoadingException(String message) {
        super(message);
    }

    public SceneLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SceneLoadingException(Throwable cause) {
        super(cause);
    }
}
