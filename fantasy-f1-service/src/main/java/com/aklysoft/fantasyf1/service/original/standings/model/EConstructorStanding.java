package com.aklysoft.fantasyf1.service.original.standings.model;

import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EConstructorStanding {

  @JsonProperty("Constructor")
  private EConstructor constructor;

  private int position;
  private int points;
  private int wins;

}
