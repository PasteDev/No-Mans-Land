package com.farcr.nomansland.common.networking;

import com.farcr.nomansland.NoMansLand;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClientboundBandageSoundPacket(int playerId) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, ClientboundBandageSoundPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, ClientboundBandageSoundPacket::playerId,
        ClientboundBandageSoundPacket::new
    );

    public static final Type<ClientboundBandageSoundPacket> TYPE = new Type<>(NoMansLand.location("client/bandage_sound"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handleData(final IPayloadContext context) {
        if (context.flow().isClientbound() && FMLEnvironment.dist == Dist.CLIENT) {
            context.enqueueWork(() -> ClientboundBandageSoundPacketClient.handle(this, context));
        }
    }
}
