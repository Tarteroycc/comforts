package com.illusivesoulworks.comforts.common;

import com.illusivesoulworks.comforts.common.capability.SleepDataImpl;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class SleepDataAttachment extends SleepDataImpl implements INBTSerializable<CompoundTag> {

  @Override
  public CompoundTag serializeNBT(@Nonnull HolderLookup.Provider provider) {
    return this.write();
  }

  @Override
  public void deserializeNBT(@Nonnull HolderLookup.Provider provider, @Nonnull CompoundTag nbt) {
    this.read(nbt);
  }
}
