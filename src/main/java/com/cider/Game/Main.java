package com.cider.Game;

import com.cider.Engine.Core;
import com.cider.Engine.Errors.LWJGL_Error;

public class Main {
  public static void main(String[] args) throws LWJGL_Error {
    new Core().run();
  }
}
