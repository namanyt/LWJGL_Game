package com.cider.Engine;

import com.cider.Engine.Utils.Graphics.Window.Window;
import com.cider.Engine.Utils.Logger.Logger;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Core {

  Window window;

  public Core() {
    Logger.LogInfo("Starting Core.");
    window = new Window(1270, 720, "Game Window", NULL, NULL);
  }

  public void stop() {
    window.stop();
    Logger.LogInfo("Shutting Core.");
    System.exit(0);
  }

  public void run() {
    window.init();

    while (!glfwWindowShouldClose(window.getWindow())) {
      window.loop();
    }

    stop();
  }
}
