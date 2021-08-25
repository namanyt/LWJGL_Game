package com.cider.Engine.Utils.Scene;

import com.cider.Engine.Utils.Graphics.Window.Window;

public abstract class SceneManager {
  public SceneManager() {

  }
  public void init() {}
  public abstract void update(float dt, Window window);
}