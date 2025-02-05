package com.illusivesoulworks.comforts.data;

import com.illusivesoulworks.comforts.ComfortsConstants;
import com.illusivesoulworks.comforts.common.ComfortsConfig;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record HammockEnabledCondition() implements ResourceCondition {

  public static final HammockEnabledCondition INSTANCE = new HammockEnabledCondition();

  public static MapCodec<HammockEnabledCondition> CODEC = MapCodec.unit(INSTANCE).stable();

  public static final ResourceConditionType<?> TYPE = ResourceConditionType.create(
      ResourceLocation.fromNamespaceAndPath(ComfortsConstants.MOD_ID, "hammock_enabled"),
      CODEC);

  @Override
  public ResourceConditionType<?> getType() {
    return TYPE;
  }

  @Override
  public boolean test(@Nullable HolderLookup.Provider registryLookup) {
    return ComfortsConfig.COMMON.enableHammockRecipes.get();
  }
}
