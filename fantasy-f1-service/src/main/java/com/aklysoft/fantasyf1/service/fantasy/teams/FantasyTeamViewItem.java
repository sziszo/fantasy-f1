package com.aklysoft.fantasyf1.service.fantasy.teams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasyTeamViewItem {
  private String id;
  private String username;
  private String series;
  private int season;
  private String name;
  private Long money;
  private String playerDisplayName;

}
