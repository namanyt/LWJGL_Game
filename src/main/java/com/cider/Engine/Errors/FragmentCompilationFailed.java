package com.cider.Engine.Errors;

public class FragmentCompilationFailed extends Throwable{
  public FragmentCompilationFailed() {
  }

  public FragmentCompilationFailed(String message) {
    super(message);
  }

  public FragmentCompilationFailed(String message, Throwable cause) {
    super(message, cause);
  }

  public FragmentCompilationFailed(Throwable cause) {
    super(cause);
  }

  public FragmentCompilationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
