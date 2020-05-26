package com.aklysoft.fantasyf1.service.original.constructors.model;

import com.aklysoft.fantasyf1.service.original.drivers.model.EDriver;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EConstructorTable {

  private int season;

  @JsonProperty("Constructors")
  private List<EConstructor> constructors;
}
