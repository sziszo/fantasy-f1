package com.aklysoft.fantasyf1.service.original.constructors.model;

import com.aklysoft.fantasyf1.service.original.EData;
import com.aklysoft.fantasyf1.service.original.drivers.model.EDriverTable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EConstructorData extends EData {

  @JsonProperty("ConstructorTable")
  private EConstructorTable constructorTable;

}
