package com.aklysoft.fantasyf1.service.fantasy.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasyTeamMemberPK implements Serializable {

  private String userName;
  private String series;
  private int season;
  private int round;
  private FantasyTeamMemberTypeId teamMemberTypeId;

}
