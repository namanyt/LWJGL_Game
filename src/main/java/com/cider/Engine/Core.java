package com.cider.Engine;

import com.cider.Engine.Errors.LWJGL_Error;
import com.cider.Engine.Graphics.Window.Window;
import com.cider.Engine.Utils.DiscordRPC.DiscordUtils;
import com.cider.Engine.Utils.DiscordRPC.GameState;
import com.cider.Engine.Utils.Logger.LogLevel;
import com.cider.Engine.Utils.Logger.Logger;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Core {

  ProjectMode mode = ProjectMode.Development;
  Window window;
  Logger logger;
  DiscordUtils discordUtils = new DiscordUtils();

  public Core() {
    if (mode == ProjectMode.Development)  logger = new Logger(LogLevel.TRACE);
    else if (mode == ProjectMode.Debug)   logger = new Logger(LogLevel.INFO);
    else                                  logger = new Logger(LogLevel.NoMessage);

    discordUtils.init();
    discordUtils.updateState(GameState.STARTING);

    Logger.LogSystem("Starting Core.");
    window = new Window(1270, 720, "Game Window", NULL, NULL);
  }

  public void stop() {
    window.stop();
    Logger.LogSystem("Shutting Core.");
    System.exit(0);
  }

  public void run() throws LWJGL_Error {
    window.init();

//    if (mode == ProjectMode.Debug || mode == ProjectMode.Development) Logger.TestLogging();
    discordUtils.updateState(GameState.IN_MENU);

    while (!glfwWindowShouldClose(window.getWindow())) {
      window.loop();
    }

    discordUtils.updateState(GameState.EXITING);
    stop();
  }
}
