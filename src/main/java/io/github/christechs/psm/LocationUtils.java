package io.github.christechs.psm;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.hypixel.data.type.GameType;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;

public class LocationUtils {

    private static boolean isOnHypixel = false;
    private static boolean isOnSkyblock = false;
    private static boolean isInLobby = false;

    public static void init() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> reset());

        HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);

        HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket.class, packet -> {
            isOnHypixel = true;

            if (packet.getLobbyName().isPresent()) {
                isInLobby = true;
                isOnSkyblock = false;
            } else if (packet.getServerType().isPresent()) {
                isInLobby = false;
                isOnSkyblock = packet.getServerType().get() == GameType.SKYBLOCK;
            } else {
                isInLobby = false;
                isOnSkyblock = false;
            }
        });
    }

    public static boolean isOnHypixel() {
        return isOnHypixel;
    }

    public static boolean isInLobby() {
        return isInLobby;
    }

    public static boolean isOnSkyblock() {
        return isOnSkyblock;
    }

    public static float clampScale(float scale) {
        if (isOnHypixel && !isInLobby && !isOnSkyblock) {
            return Math.min(scale, 1.0f);
        }
        return scale;
    }

    public static void reset() {
        isOnHypixel = false;
        isInLobby = false;
        isOnSkyblock = false;
    }
}