package com.aklysoft.fantasyf1.service.fantasy.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasyTeamMemberViewItem {

  private String id;
  private String teamId;
  private String username;
  private String series;
  private Integer season;
  private Integer round;
  private FantasyTeamMemberCategoryType teamMemberTypeId;
  private String driverId;
  private String constructorId;
}
