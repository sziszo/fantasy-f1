package com.aklysoft.fantasyf1.service.fantasy.definitions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasySeasonViewItem {

  private String id;
  private String series;
  private Integer season;

}
