package com.aklysoft.fantasyf1.service.fantasy.definitions;

import lombok.Data;

import java.io.Serializable;

@Data
public class FantasyDefinitionPK implements Serializable {

  private String series;
  private int season;

}
