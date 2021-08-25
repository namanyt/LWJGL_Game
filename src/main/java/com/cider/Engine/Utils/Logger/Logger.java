package com.cider.Engine.Utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.cider.Engine.Utils.UtilsManager.*;

public class Logger {
  public Logger() {
    String timeStamp = new SimpleDateFormat("[HH:mm:ss a]  ").format(new Date());
  }

  public static void LogTrace(String message) {
    String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + TracePrefix + message);
  }

  public static void LogInfo(String message) {
    String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + InfoPrefix + message);
  }

  public static void LogWarn(String message) {
    String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + WarnPrefix + message);
  }

  public static void LogError(Throwable exception) {
    String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    String exceptionString = "[" + ConsoleColors.RED_BOLD + exception.getClass().getName() + ConsoleColors.RESET + "] ";
    System.out.println(timestamp + ErrorPrefix + exceptionString + exception.getMessage());
  }

  public static void LogCritical(Throwable error) {
    String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    String exceptionString = "[" + ConsoleColors.RED_BOLD + error.getClass().getName() + ConsoleColors.RESET + "] ";
    System.out.println(timestamp + CriticalPrefix + exceptionString + error.getMessage());
//    System.exit(-1);
  }

  public static void LogCrash(Throwable throwable) {
    String timestamp = ConsoleColors.WHITE_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + CrashReportPrefix + throwable.getMessage());
  }
}
