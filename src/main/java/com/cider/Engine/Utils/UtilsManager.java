package com.cider.Engine.Utils;

import com.cider.Engine.Utils.Logger.ConsoleColors;

public class UtilsManager {
    public static String SystemPrefix = ConsoleColors.BLACK_BOLD + "[SYSTEM]" + ConsoleColors.RESET + " ";
    public static String TracePrefix = ConsoleColors.WHITE_BOLD + "[TRACE]" + ConsoleColors.RESET + " ";
    public static String InfoPrefix = ConsoleColors.GREEN_BOLD + "[INFO]" + ConsoleColors.RESET + " ";
    public static String WarnPrefix = ConsoleColors.YELLOW_BOLD + "[WARN]" + ConsoleColors.RESET + " ";
    public static String ErrorPrefix = ConsoleColors.RED_BOLD_BRIGHT + "[ERROR]" + ConsoleColors.RESET + " ";
    public static String CriticalPrefix = ConsoleColors.RED_BOLD_BRIGHT + "[FATAL]" + ConsoleColors.RESET + " ";
    public static String CrashReportPrefix = ConsoleColors.RED_BOLD_BRIGHT + "[CRASH]" + ConsoleColors.RESET + " ";
}
