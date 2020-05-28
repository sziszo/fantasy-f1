package com.aklysoft.fantasyf1.service.original.races.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ERaceTable {

  private int season;

  @JsonProperty("Races")
  private List<ERace> races;
}
