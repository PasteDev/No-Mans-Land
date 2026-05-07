package com.farcr.nomansland.common.networking;

import com.farcr.nomansland.client.sound.BandageSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ClientboundBandageSoundPacketClient {
    private ClientboundBandageSoundPacketClient() {
    }

    public static void handle(ClientboundBandageSoundPacket packet, IPayloadContext context) {
        Entity entity = context.player().level().getEntity(packet.playerId());
        if (entity instanceof Player player) {
            Minecraft.getInstance().getSoundManager().play(new BandageSoundInstance(player));
        }
    }
}
