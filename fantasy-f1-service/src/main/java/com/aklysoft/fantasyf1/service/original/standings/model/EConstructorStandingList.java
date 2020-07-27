package com.aklysoft.fantasyf1.service.original.standings.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EConstructorStandingList {

  private int season;
  private int round;

  @JsonProperty("ConstructorStandings")
  private List<EConstructorStanding> constructorStandings;


}
