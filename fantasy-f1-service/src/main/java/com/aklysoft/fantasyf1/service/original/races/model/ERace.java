package com.aklysoft.fantasyf1.service.original.races.model;

import com.aklysoft.fantasyf1.service.original.results.model.ERaceResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERace {

  private int season;
  private int round;

  private String raceName;
  private String date;
  private String time;

  @JsonProperty("Circuit")
  private ECircuitData circuitData;

  @JsonProperty("Results")
  private List<ERaceResult> raceResults;

}
