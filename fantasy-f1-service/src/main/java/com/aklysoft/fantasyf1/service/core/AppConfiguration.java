package com.aklysoft.fantasyf1.service.core;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "app")
public class AppConfiguration {

  public String showStacktrace = "false";

  public Integer downloadLimit = 30;

}
