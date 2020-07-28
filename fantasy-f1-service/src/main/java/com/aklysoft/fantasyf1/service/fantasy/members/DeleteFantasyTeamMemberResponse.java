package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamViewItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFantasyTeamMemberResponse {

  @JsonProperty("fantasyTeamItem")
  private FantasyTeamViewItem fantasyTeamViewItem;

  @JsonProperty("fantasyTeamMemberItem")
  private FantasyTeamMemberViewItem fantasyTeamMemberViewItem;
}
