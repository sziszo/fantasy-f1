package com.aklysoft.fantasyf1.service.original.results.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EFastestLap {

  int rank;
  int lap;

  @JsonProperty("Time")
  private ETime lapTime;

  @JsonProperty("AverageSpeed")
  private ELapAverageSpeed lapAverageSpeed;
}
