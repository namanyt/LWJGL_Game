package com.cider.Engine.Errors;

public class LWJGL_Error extends Throwable {

    public LWJGL_Error() {
    }

    public LWJGL_Error(String message) {
        super(message);
    }

    public LWJGL_Error(String message, Throwable cause) {
        super(message, cause);
    }

    public LWJGL_Error(Throwable cause) {
        super(cause);
    }

    public LWJGL_Error(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
