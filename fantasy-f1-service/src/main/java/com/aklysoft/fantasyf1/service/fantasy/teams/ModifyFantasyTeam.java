package com.aklysoft.fantasyf1.service.fantasy.teams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ModifyFantasyTeam {

  private String username;
  private String series;
  private Integer season;

  private String name;

  private String modifier;

}
