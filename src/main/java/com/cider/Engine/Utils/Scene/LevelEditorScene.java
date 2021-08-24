package com.cider.Engine.Utils.Scene;

import com.cider.Engine.Utils.Controls.KeyListener;
import com.cider.Engine.Utils.Graphics.Window.Window;
import com.cider.Engine.Utils.Logger.Logger;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends SceneManager {
  private boolean changingScene = false;
  private float timeToChangeScene = 2.0f;

  public LevelEditorScene() {
    Logger.LogInfo("Inside level editor scene.");
  }

  @Override
  public void update(float dt, Window window) {
    if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
      changingScene = true;
    }

    if (changingScene && timeToChangeScene > 0) {
      timeToChangeScene -= dt;
      window.r -= dt * 0.5f;
      window.g -= dt * 0.5f;
      window.b -= dt * 0.5f;
    } else if (changingScene) {
      Window.changeScene(1);
    }
  }
}
