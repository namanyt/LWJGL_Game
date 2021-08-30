package com.cider.Engine.Utils.DiscordRPC;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import com.cider.Engine.Utils.Logger.Logger;

import java.util.Locale;

import static org.lwjgl.system.libc.LibCString.memset;

public class DiscordUtils {
  private final String applicationId = "859821675536711700";
  private final String steamId = "";

  // Discord Stuff
  private final DiscordRichPresence presence = new DiscordRichPresence();
  private final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
  private final DiscordEventHandlers handlers = new DiscordEventHandlers();

  // Thread Discord Stuff

  Thread t = new Thread(this::update, "RPC-CallbackHandler");

  public DiscordUtils() {
    handlers.joinRequest = (a) -> {
      Logger.LogInfo("Discord RPC [SENT REQUEST]");
    };

    handlers.spectateGame = (a) -> {
      Logger.LogInfo("Discord RPC [SPECTATING GAME]");
    };

    handlers.errored = (a, b) -> {
      Logger.LogWarn("Discord RPC [ERROR]");
    };

    handlers.ready = (user) -> {
      Logger.LogInfo("Discord RPC [CONNECTED]");
    };

    handlers.disconnected = (errorCode, message) -> {
      Logger.LogInfo("Discord RPC [DISCONNECTED] (" + errorCode + ")");
    };
    handlers.joinGame = secret -> {
      Logger.LogInfo("Discord RPC [JOINED]");
    };
  }

  public void init() {
    Logger.LogInfo("Discord RPC [INITIALIZING]");
    discordRPC.Discord_Initialize(applicationId, handlers, true, steamId);

    updateDetails("LWJGL_Game");
    presence.largeImageKey  = "lwjgl";
    presence.largeImageText = "LWJGL Game";
    presence.smallImageKey  = "lwjgl2";
    presence.smallImageText = "LWJGL";
    presence.partyId = "ae488379-351d-4a4f-a32-2b9b01c91657";
    discordRPC.Discord_UpdateHandlers(handlers);
    discordRPC.Discord_UpdatePresence(presence);

    t.start();
  }

  void update() {
    while (!Thread.currentThread().isInterrupted()) {
      discordRPC.Discord_RunCallbacks();
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        discordRPC.Discord_Shutdown();
        break;
      }
    }
  }

  public void updateDetails(String text) {
    presence.details = text;
  }

  public void updateState(GameState state) {
    switch (state) {
      case STARTING -> {
        presence.state = "Starting";
        discordRPC.Discord_UpdatePresence(presence);
      }
      case IN_MENU  -> {
        presence.state = "In Menus";
        discordRPC.Discord_UpdatePresence(presence);
      }
      case EXITING  -> {
        presence.state = "Exiting";
        discordRPC.Discord_UpdatePresence(presence);
      }
    }
  }
}
