package com.aklysoft.fantasyf1.service.original.standings.model;

import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EDriverStanding {

  @JsonProperty("Driver")
  private EDriver driver;

  private int position;
  private int points;
  private int wins;

}
