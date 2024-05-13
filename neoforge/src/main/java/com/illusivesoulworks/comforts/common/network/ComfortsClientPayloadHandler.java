package com.illusivesoulworks.comforts.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ComfortsClientPayloadHandler {

  private static final ComfortsClientPayloadHandler INSTANCE =
      new ComfortsClientPayloadHandler();

  public static ComfortsClientPayloadHandler getInstance() {
    return INSTANCE;
  }

  public void handleAutoSleep(SPacketAutoSleep msg, IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
          ClientLevel level = Minecraft.getInstance().level;

          if (level != null) {
            Entity entity = level.getEntity(msg.entityId());

            if (entity instanceof final Player player) {
              SPacketAutoSleep.handle(player, msg.pos());
            }
          }
        })
        .exceptionally(e -> {
          ctx.disconnect(Component.translatable("comforts.networking.failed", e.getMessage()));
          return null;
        });
  }

  public void handlePlaceBag(SPacketPlaceBag msg, IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
          ClientLevel level = Minecraft.getInstance().level;

          if (level != null) {
            Entity entity = level.getEntity(msg.entityId());

            if (entity instanceof final Player player) {
              SPacketPlaceBag.handle(player, msg.hand(),
                  new BlockHitResult(new Vec3(msg.location()), msg.direction(), msg.blockPos(),
                      msg.inside()));
            }
          }
        })
        .exceptionally(e -> {
          ctx.disconnect(Component.translatable("comforts.networking.failed", e.getMessage()));
          return null;
        });
  }
}
