package com.aklysoft.fantasyf1.service.original.drivers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EDriverTable {

  private int season;

  @JsonProperty("Drivers")
  private List<EDriver> drivers;
}
