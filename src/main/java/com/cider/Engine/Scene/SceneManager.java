package com.cider.Engine.Scene;

import com.cider.Engine.Camera.Camera;
import com.cider.Engine.Graphics.Window.Window;

public abstract class SceneManager {
  protected Camera camera;
  public String name;

  public SceneManager() {

  }

  public void init() {}
  public abstract void update(float dt, Window window);
}
