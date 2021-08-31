package com.cider.Engine.Errors.Test;

public class TestError extends Error {
    public TestError() {
        super();
    }

    public TestError(String message) {
        super(message);
    }

    public TestError(String message, Throwable cause) {
        super(message, cause);
    }

    public TestError(Throwable cause) {
        super(cause);
    }

    protected TestError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
