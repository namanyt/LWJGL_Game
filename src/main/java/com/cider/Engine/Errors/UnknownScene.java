package com.cider.Engine.Errors;

public class UnknownScene extends Throwable {
    public UnknownScene() {
    }

    public UnknownScene(String message) {
        super(message);
    }

    public UnknownScene(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownScene(Throwable cause) {
        super(cause);
    }

    public UnknownScene(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
