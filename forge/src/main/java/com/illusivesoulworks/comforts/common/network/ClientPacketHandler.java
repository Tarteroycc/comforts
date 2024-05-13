package com.illusivesoulworks.comforts.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ClientPacketHandler {

  public static void handle(SPacketAutoSleep msg) {
    ClientLevel level = Minecraft.getInstance().level;

    if (level != null) {
      Entity entity = level.getEntity(msg.entityId());

      if (entity instanceof final Player player) {
        SPacketAutoSleep.handle(player, msg.pos());
      }
    }
  }

  public static void handle(SPacketPlaceBag msg) {
    ClientLevel level = Minecraft.getInstance().level;

    if (level != null) {
      Entity entity = level.getEntity(msg.entityId());

      if (entity instanceof final Player player) {
        SPacketPlaceBag.handle(player, msg.hand(),
            new BlockHitResult(new Vec3(msg.location()), msg.direction(), msg.blockPos(),
                msg.inside()));
      }
    }
  }
}
