package com.aklysoft.fantasyf1.service.original.races.model;

import com.aklysoft.fantasyf1.service.original.EData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ERaceData extends EData {

  @JsonProperty("RaceTable")
  private ERaceTable raceTable;

}
