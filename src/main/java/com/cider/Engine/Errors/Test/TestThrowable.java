package com.cider.Engine.Errors.Test;

public class TestThrowable extends Throwable{
  public TestThrowable() {
  }

  public TestThrowable(String message) {
    super(message);
  }

  public TestThrowable(String message, Throwable cause) {
    super(message, cause);
  }

  public TestThrowable(Throwable cause) {
    super(cause);
  }

  public TestThrowable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
