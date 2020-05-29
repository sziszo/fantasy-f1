package com.aklysoft.fantasyf1.service.fantasy.teams;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FantasyTeamPK implements Serializable {

  private String userName;
  private String series;
  private int season;

}
