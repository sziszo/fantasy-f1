package com.aklysoft.fantasyf1.service.original.results.model;

import com.aklysoft.fantasyf1.service.original.constructors.model.EConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ERaceResult {

  private int position;
  private int number;
  private String positionText;
  private int points;

  private int grid;
  private int laps;
  private String status;

  @JsonProperty("Driver")
  private EDriver driver;

  @JsonProperty("Constructor")
  private EConstructor constructor;

  @JsonProperty("Time")
  private ETime time;

  @JsonProperty("FastestLap")
  private EFastestLap fastestLap;

  ERaceResult(ERaceResult raceResult) {
    this.position = raceResult.position;
    this.number = raceResult.number;
    this.positionText = raceResult.positionText;
    this.points = raceResult.points;
    this.grid = raceResult.grid;
    this.laps = raceResult.laps;
    this.status = raceResult.status;
    this.driver = raceResult.driver;
    this.constructor = raceResult.constructor;
    this.time = raceResult.time;
    this.fastestLap = raceResult.fastestLap;
  }


}
