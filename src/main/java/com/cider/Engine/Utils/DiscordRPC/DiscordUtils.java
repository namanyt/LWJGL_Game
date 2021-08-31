package com.cider.Engine.Utils.DiscordRPC;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.cider.Engine.Utils.Logger.ConsoleColors;
import com.cider.Engine.Utils.Logger.Logger;

import java.util.UUID;

public class DiscordUtils {
    // ids
    private final UUID joinId, partyId, matchId;

    // Discord Stuff
    private final DiscordRichPresence presence = new DiscordRichPresence();
    private final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private final DiscordEventHandlers handlers = new DiscordEventHandlers();

    // Thread Discord Stuff
    Thread t = new Thread(this::update, "RPC-CallbackHandler");

    public DiscordUtils() {

        joinId = UUID.randomUUID();
        partyId = UUID.randomUUID();
        matchId = UUID.randomUUID();

        handlers.joinRequest = (a) -> {
            Logger.LogInfo("Discord RPC [SENT REQUEST]");
        };

        handlers.spectateGame = (a) -> {
            Logger.LogInfo("Discord RPC [SPECTATING GAME]");
        };

        handlers.errored = (errorCode, message) -> {
            Logger.LogWarn("Discord RPC [ERROR (" + errorCode + ")]: " + ConsoleColors.RED + message + ConsoleColors.RESET);
        };

        handlers.ready = (user) -> {
            discordRPC.Discord_Respond(user.userId, DiscordRPC.DISCORD_REPLY_YES);
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
        String applicationId = "859821675536711700";
        String steamId = "";
        discordRPC.Discord_Initialize(applicationId, handlers, true, steamId);

        updateDetails("LWJGL_Game");
        presence.largeImageKey = "lwjgl";
        presence.largeImageText = "LWJGL Game";
        presence.smallImageKey = "lwjgl2";
        presence.smallImageText = "LWJGL";
        presence.partyId = partyId.toString();
        presence.joinSecret = joinId.toString();
        presence.matchSecret = matchId.toString();
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
            case IN_MENU -> {
                presence.state = "In Menus";
                discordRPC.Discord_UpdatePresence(presence);
            }
            case EXITING -> {
                presence.state = "Exiting";
                discordRPC.Discord_UpdatePresence(presence);
            }
        }
    }
}
