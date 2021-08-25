package com.cider.Engine.Errors;

public class GLSLFileNotFound extends Throwable {
  public GLSLFileNotFound() {
  }

  public GLSLFileNotFound(String message) {
    super(message);
  }

  public GLSLFileNotFound(String message, Throwable cause) {
    super(message, cause);
  }

  public GLSLFileNotFound(Throwable cause) {
    super(cause);
  }

  public GLSLFileNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
