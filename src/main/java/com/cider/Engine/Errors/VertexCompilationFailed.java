package com.cider.Engine.Errors;

public class VertexCompilationFailed extends Throwable {
    public VertexCompilationFailed() {
    }

    public VertexCompilationFailed(String message) {
        super(message);
    }

    public VertexCompilationFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public VertexCompilationFailed(Throwable cause) {
        super(cause);
    }

    public VertexCompilationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
