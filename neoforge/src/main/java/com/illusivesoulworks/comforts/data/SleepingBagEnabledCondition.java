package com.illusivesoulworks.comforts.data;

import com.illusivesoulworks.comforts.common.ComfortsConfig;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nonnull;
import net.neoforged.neoforge.common.conditions.ICondition;

public record SleepingBagEnabledCondition() implements ICondition {

  public static final SleepingBagEnabledCondition INSTANCE = new SleepingBagEnabledCondition();

  public static MapCodec<SleepingBagEnabledCondition> CODEC = MapCodec.unit(INSTANCE).stable();

  @Override
  public boolean test(@Nonnull IContext context) {
    return ComfortsConfig.COMMON.enableSleepingBag.get();
  }

  @Nonnull
  @Override
  public MapCodec<? extends ICondition> codec() {
    return CODEC;
  }
}
