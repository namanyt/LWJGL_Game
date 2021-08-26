package com.cider.Engine.Controls;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
  private static KeyListener instance;
  private final boolean[] keyPressed = new boolean[500];

  private KeyListener() {
  }

  public static KeyListener get() {
    if (KeyListener.instance == null) {
      KeyListener.instance = new KeyListener();
    }

    return KeyListener.instance;
  }

  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    if (action == GLFW_PRESS) {
      if (key < get().keyPressed.length && key > 0) {
        get().keyPressed[key] = true;
      }
    } else if (action == GLFW_RELEASE) {
      if (key < get().keyPressed.length && key > 0) {
        get().keyPressed[key] = false;
      }
    }
  }

  public static boolean isKeyPressed(int keyCode) {
    return get().keyPressed[keyCode];
  }
}
