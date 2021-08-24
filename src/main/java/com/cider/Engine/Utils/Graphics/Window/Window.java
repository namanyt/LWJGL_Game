package com.cider.Engine.Utils.Graphics.Window;

import com.cider.Engine.Errors.LWJGL_Error;
import com.cider.Engine.Utils.Controls.KeyListener;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private long window;
  int width, height;
  String title;
  long monitor, share;

  public Window(int width, int height, String title, long monitor, long share) {
    this.width = width;
    this.height = height;
    this.title = title;
    this.monitor = monitor;
    this.share = share;
  }

  public void stop() {
    glfwFreeCallbacks(window);
    glfwDestroyWindow(window);
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  public void init() {
    GLFWErrorCallback.createPrint(System.err).set();
    if (!glfwInit())
      try {
        throw new LWJGL_Error("Unable to initialize GLFW");
      } catch (LWJGL_Error e) {
        e.printStackTrace();
      }

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    window = glfwCreateWindow(this.width, this.height, this.title, this.monitor, this.share);
    if (window == NULL)
      throw new RuntimeException("Failed to create the GLFW window");

    setKeyCallbacks();

    ManageMemoryAndCenterWindow();

    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    glfwShowWindow(window);
    GL.createCapabilities();
  }

  public void setKeyCallbacks() {
    glfwSetCursorPosCallback    (window, com.cider.Engine.Utils.Controls.MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback  (window, com.cider.Engine.Utils.Controls.MouseListener::mouseButtonCallback);
    glfwSetScrollCallback       (window, com.cider.Engine.Utils.Controls.MouseListener::mouseScrollCallback);
    glfwSetKeyCallback          (window, com.cider.Engine.Utils.Controls.KeyListener::keyCallback);
  }

  public void loop() {
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


    if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
      glfwSetWindowShouldClose(window, true);
    }

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glfwSwapBuffers(window);

    glfwPollEvents();
  }

  private void ManageMemoryAndCenterWindow() {
    try (MemoryStack stack = stackPush()) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      glfwGetWindowSize(window, pWidth, pHeight);

      GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      glfwSetWindowPos(
              window,
              (vidmode.width() - pWidth.get(0)) / 2,
              (vidmode.height() - pHeight.get(0)) / 2
      );
    }
  }

  public long getWindow() {
    return window;
  }
}
