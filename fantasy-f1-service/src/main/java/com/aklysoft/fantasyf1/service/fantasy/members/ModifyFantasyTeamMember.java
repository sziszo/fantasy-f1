package com.aklysoft.fantasyf1.service.fantasy.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyFantasyTeamMember {

  private int race;
  private FantasyTeamMemberTypeId teamMemberTypeId;

  private String id;  //driverId or constructorId
}
