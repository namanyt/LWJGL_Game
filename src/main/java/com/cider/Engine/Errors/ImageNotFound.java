package com.cider.Engine.Errors;

public class ImageNotFound extends Throwable{
  public ImageNotFound() {
  }

  public ImageNotFound(String message) {
    super(message);
  }

  public ImageNotFound(String message, Throwable cause) {
    super(message, cause);
  }

  public ImageNotFound(Throwable cause) {
    super(cause);
  }

  public ImageNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
