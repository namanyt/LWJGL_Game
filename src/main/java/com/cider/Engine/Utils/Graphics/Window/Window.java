package com.cider.Engine.Utils.Graphics.Window;

import com.cider.Engine.Errors.LWJGL_Error;
import com.cider.Engine.Utils.Controls.KeyListener;
import com.cider.Engine.Utils.General.Time;
import com.cider.Engine.Utils.Scene.LevelEditorScene;
import com.cider.Engine.Utils.Scene.LevelScene;
import com.cider.Engine.Utils.Scene.SceneManager;
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
  private static SceneManager currentScene;
  public float r = 1.0f;
  public float g = 1.0f;
  public float b = 1.0f;
  public float a = 1.0f;
  int width, height;
  String title;
  long monitor, share;
  float beginTime = Time.getTime();
  float endTime;
  float dt = -1.0f;
  private long window;

  public Window(int width, int height, String title, long monitor, long share) {
    this.width = width;
    this.height = height;
    this.title = title;
    this.monitor = monitor;
    this.share = share;
  }

  public static void changeScene(int newScene) {
    switch (newScene) {
      case 0:
        currentScene = new LevelEditorScene();
        break;

      case 1:
        currentScene = new LevelScene();
        break;

      default:
        assert false : "Unknown scene: " + newScene;
        break;
    }
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

    Window.changeScene(0);
  }

  public void setKeyCallbacks() {
    glfwSetCursorPosCallback(window, com.cider.Engine.Utils.Controls.MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(window, com.cider.Engine.Utils.Controls.MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(window, com.cider.Engine.Utils.Controls.MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(window, com.cider.Engine.Utils.Controls.KeyListener::keyCallback);
  }

  public void loop() {
    if (dt >= 0) {
      currentScene.update(dt, this);
    }

    if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
      glfwSetWindowShouldClose(window, true);
    }
    glClearColor(r, g, b, a);

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glfwSwapBuffers(window);

    glfwPollEvents();

    endTime = Time.getTime();
    dt = endTime - beginTime;
    beginTime = endTime;
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
