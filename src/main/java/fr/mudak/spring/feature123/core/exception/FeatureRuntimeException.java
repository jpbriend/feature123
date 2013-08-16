package fr.mudak.spring.feature123.core.exception;


public class FeatureRuntimeException extends RuntimeException {
    public FeatureRuntimeException() {
        super();
    }

    public FeatureRuntimeException(String message) {
        super(message);
    }

    public FeatureRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeatureRuntimeException(Throwable cause) {
        super(cause);
    }
}
