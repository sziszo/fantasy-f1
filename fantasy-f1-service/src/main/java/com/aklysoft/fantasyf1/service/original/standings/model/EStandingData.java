package com.aklysoft.fantasyf1.service.original.standings.model;

import com.aklysoft.fantasyf1.service.original.EData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EStandingData<T> extends EData {

  @JsonProperty("StandingsTable")
  private EStandingTable<T> standingTable;
}
