package com.cider.Engine.Scene;

import com.cider.Engine.Camera.Camera;
import com.cider.Engine.Components.GameObject;
import com.cider.Engine.Graphics.Window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class SceneManager {
    protected Camera camera;
    public String name;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

    public SceneManager() {

    }

    public void start() {
        for (GameObject gameObject : gameObjects) {
            gameObject.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject gameObject) {
        if (!isRunning) {
            gameObjects.add(gameObject);
        } else {
            gameObjects.add(gameObject);
            gameObject.start();
        }
    }

    public void init() {
    }

    public abstract void update(float dt, Window window);
}
