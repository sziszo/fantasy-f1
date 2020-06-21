package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;
import com.aklysoft.fantasyf1.service.players.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.aklysoft.fantasyf1.service.core.utils.StringUtils.SEPARATOR;
import static java.util.stream.Collectors.toList;

public class FantasyTeamMappers {


  public static List<FantasyTeamViewItem> toFantasyTeamViewItem(List<FantasyTeam> fantasyTeams) {
    return toFantasyTeamViewItem(fantasyTeams.stream()).collect(toList());
  }

  public static FantasyTeamViewItem toFantasyTeamViewItem(FantasyTeam fantasyTeam) {
    if (fantasyTeam == null) {
      return null;
    }

    return toFantasyTeamViewItem(Stream.of(fantasyTeam))
            .findFirst()
            .orElseThrow(() -> new ApiException("" +
                    ""));
  }


  public static Stream<FantasyTeamViewItem> toFantasyTeamViewItem(Stream<FantasyTeam> fantasyTeams) {
    return fantasyTeams
            .map(fantasyTeam ->
                    FantasyTeamViewItem
                            .builder()
                            .id(createFantasyTeamViewId(fantasyTeam))
                            .series(fantasyTeam.getSeries())
                            .season(fantasyTeam.getSeason())
                            .name(fantasyTeam.getName())
                            .username(fantasyTeam.getUserName())
                            .playerDisplayName(
                                    Optional.ofNullable(fantasyTeam.getPlayer())
                                            .orElse(Player.builder().build())
                                            .getFullName())
                            .build()
            );
  }

  public static String createFantasyTeamViewId(FantasyTeam fantasyTeam) {
    return createFantasyTeamViewId(fantasyTeam.getSeries(), fantasyTeam.getSeason(), fantasyTeam.getUserName());
  }

  public static FantasyTeamPK createFantasyTeamId(FantasyTeam fantasyTeam) {
    return FantasyTeamPK.builder()
            .series(fantasyTeam.getSeries())
            .season(fantasyTeam.getSeason())
            .userName(fantasyTeam.getUserName())
            .build();
  }

  public static String createFantasyTeamViewId(String series, int season, String username) {
    return "ft" + SEPARATOR + series + SEPARATOR + season + SEPARATOR + username;
  }
}
