package com.aklysoft.fantasyf1.service.fantasy.teams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FantasyTeamMappersTest {

  @Test
  void testToFantasyTeamViewItemWhenFantasyTeamIsNull() {
    assertNull(FantasyTeamMappers.toFantasyTeamViewItem((FantasyTeam) null));
  }

  @Test
  void testCreateFantasyTeamId() {
    //given
    final String series = "f1";
    final int season = 2019;
    final String userName = "user";

    final FantasyTeam fantasyTeam = new FantasyTeam();
    fantasyTeam.setSeries(series);
    fantasyTeam.setSeason(season);
    fantasyTeam.setUserName(userName);

    final FantasyTeamPK expected = FantasyTeamPK.builder().series(series).season(season).userName(userName).build();

    //when & then
    assertEquals(expected, FantasyTeamMappers.createFantasyTeamId(fantasyTeam));
  }

}
