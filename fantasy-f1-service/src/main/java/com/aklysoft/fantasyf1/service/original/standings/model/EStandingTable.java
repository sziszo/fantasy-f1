package com.aklysoft.fantasyf1.service.original.standings.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EStandingTable<T> {
  private int season;

  @JsonProperty("StandingsLists")
  private List<T> standingLists;
}
