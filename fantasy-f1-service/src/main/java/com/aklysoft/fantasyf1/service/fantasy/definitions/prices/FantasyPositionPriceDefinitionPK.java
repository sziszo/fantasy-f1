package com.aklysoft.fantasyf1.service.fantasy.definitions.prices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasyPositionPriceDefinitionPK implements Serializable {
  private String series;
  private Integer season;
  private Integer position;
  private FantasyPositionPriceDefinitionType type;
}
