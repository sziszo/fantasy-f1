package com.aklysoft.fantasyf1.service.original.drivers.model;

import com.aklysoft.fantasyf1.service.original.EData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EDriverData extends EData {

  @JsonProperty("DriverTable")
  private EDriverTable driverTable;

}
