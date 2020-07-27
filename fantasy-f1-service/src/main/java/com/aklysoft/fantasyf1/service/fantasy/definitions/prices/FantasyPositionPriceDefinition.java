package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(FantasyPositionPriceDefinitionPK.class)
public class FantasyPositionPriceDefinition {

  @Id
  private String series;

  @Id
  private Integer season;

  @Id
  private Integer position;

  @Id
  @Enumerated(EnumType.STRING)
  private FantasyPositionPriceDefinitionType type;


  private Integer percent;
}
