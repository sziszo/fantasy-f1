package com.aklysoft.fantasyf1.service.fantasy.definitions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(FantasyDefinitionPK.class)
public class FantasyDefinition {

  @Id
  private String series;

  @Id
  private Integer season;

  private Integer nextRace;


}
