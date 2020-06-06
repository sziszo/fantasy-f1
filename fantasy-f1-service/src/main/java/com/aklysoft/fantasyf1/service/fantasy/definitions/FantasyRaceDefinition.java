package com.aklysoft.fantasyf1.service.fantasy.definitions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasyRaceDefinition {
  private String series;
  private Integer season;
  private List<FantasyRaceViewItem> fantasyRaces;
  private String selectedFantasyRaceId;
}
