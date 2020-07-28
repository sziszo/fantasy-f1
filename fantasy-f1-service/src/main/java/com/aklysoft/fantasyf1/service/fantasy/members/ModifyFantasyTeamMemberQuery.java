package com.aklysoft.fantasyf1.service.fantasy.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyFantasyTeamMemberQuery {

  private FantasyTeamMemberCategoryType teamMemberTypeId;

  private String id;  //driverId or constructorId
}
