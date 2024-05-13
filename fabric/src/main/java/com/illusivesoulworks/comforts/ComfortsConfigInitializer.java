package com.illusivesoulworks.comforts;

import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

public class ComfortsConfigInitializer implements SpectreConfigInitializer {

  @Override
  public void onInitializeConfig() {
    ComfortsCommonMod.initConfig();
  }
}
