package com.cider.Engine.Utils.Logger;

import com.cider.Engine.Errors.Test.TestError;
import com.cider.Engine.Errors.Test.TestThrowable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cider.Engine.Utils.UtilsManager.*;

public class Logger {
  static LogLevel level;
  public static boolean isDisabled;

  public Logger(LogLevel level) {
    Logger.level = level;
    if (Logger.level == LogLevel.NONE && !isDisabled) Logger.level = LogLevel.TRACE;
    String timeStamp = new SimpleDateFormat("[HH:mm:ss a]  ").format(new Date());
  }

  public static void LogSystem(String message) {
    if (!isDisabled) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      System.out.println(timestamp + SystemPrefix + message);
    }
  }

  public static void LogTrace(String message) {
    if (!isDisabled && level == LogLevel.TRACE && Logger.level != LogLevel.NoMessage) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      System.out.println(timestamp + TracePrefix + message);
    }
  }

  public static void LogInfo(String message) {
    if (!isDisabled && level == LogLevel.TRACE || level == LogLevel.INFO && Logger.level != LogLevel.NoMessage) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      System.out.println(timestamp + InfoPrefix + message);
    }
  }

  public static void LogWarn(String message) {
    if (!isDisabled && level == LogLevel.TRACE || level == LogLevel.INFO || level == LogLevel.WARN && Logger.level != LogLevel.NoMessage) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      System.out.println(timestamp + WarnPrefix + message);
    }
  }

  public static void LogError(Throwable exception) {
    if (!isDisabled && level == LogLevel.TRACE || level == LogLevel.INFO || level == LogLevel.WARN || level == LogLevel.ERROR && Logger.level != LogLevel.NoMessage) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      String exceptionString = "[" + ConsoleColors.RED_BOLD + exception.getClass().getName() + ConsoleColors.RESET + "] ";
      System.out.println(timestamp + ErrorPrefix + exceptionString + exception.getMessage());
    }
  }

  public static void LogCritical(Throwable error) {
    if (!isDisabled && level == LogLevel.TRACE || level == LogLevel.INFO || level == LogLevel.WARN || level == LogLevel.ERROR || level == LogLevel.FATAL && Logger.level != LogLevel.NoMessage) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      String exceptionString = "[" + ConsoleColors.RED_BOLD + error.getClass().getName() + ConsoleColors.RESET + "] ";
      System.out.println(timestamp + CriticalPrefix + exceptionString + error.getMessage());
      System.exit(-1);
    }
  }

  public static void LogCrash(Throwable throwable) {
    if (!isDisabled && level == LogLevel.TRACE || level == LogLevel.INFO || level == LogLevel.WARN || level == LogLevel.ERROR || level == LogLevel.FATAL || level == LogLevel.CRASH && Logger.level != LogLevel.NoMessage) {
      String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
      String exceptionString = "[" + ConsoleColors.RED_BOLD + throwable.getClass().getName() + ConsoleColors.RESET + "]\n";
      System.out.println(timestamp + CrashReportPrefix + exceptionString + throwable.getMessage());
    }
  }

  public static void TestLogging() {
    Logger.LogSystem("Test");
    Logger.LogTrace("Test");
    Logger.LogInfo("Test");
    Logger.LogWarn("Test");
    Logger.LogError(new TestError("Test"));
    Logger.LogCritical(new TestError("Test"));
    Logger.LogCrash(new TestThrowable(
            """
            This is a Test. 1
            This is a Test. 2
            This is a Test. 3
            This is a Test. 4
            This is a Test. 5
            This is a Test. 6
            """
    ));
  }
}
