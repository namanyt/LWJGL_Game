package com.cider.Engine.Utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cider.Engine.Utils.UtilsManager.*;

public class Logger {
  public Logger() {
    String timeStamp = new SimpleDateFormat("[HH:mm:ss a]  ").format(new Date());
  }

  public static void LogTrace(String message) {
    String timestamp = ConsoleColors.BLACK_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + TracePrefix + message);
  }

  public static void LogInfo(String message) {
    String timestamp = ConsoleColors.BLACK_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + InfoPrefix + message);
  }

  public static void LogWarn(String message) {
    String timestamp = ConsoleColors.BLACK_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + WarnPrefix + message);
  }

  public static void LogError(String message) {
    String timestamp = ConsoleColors.BLACK_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + ErrorPrefix + message);
  }

  public static void LogCritical(String message) {
    String timestamp = ConsoleColors.BLACK_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + CriticalPrefix + message);
  }

  public static void LogCrash(String message) {
    String timestamp = ConsoleColors.BLACK_BOLD + new SimpleDateFormat("[HH:mm:ss a] ").format(new Date()) + ConsoleColors.RESET;
    System.out.println(timestamp + CrashReportPrefix + message);
  }
}
