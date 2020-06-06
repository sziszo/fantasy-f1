package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.core.exceptions.ApiException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.aklysoft.fantasyf1.service.core.utils.StringUtils.SEPARATOR;
import static com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamMappers.createFantasyTeamViewId;
import static com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorMappers.toConstructorViewItemId;
import static com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverMappers.toDriverViewItemId;
import static java.util.stream.Collectors.toList;

public class FantasyTeamMemberMappers {

  public static FantasyTeamMemberViewItem toFantasyTeamMemberViewItem(FantasyTeamMember fantasyTeamMember) {
    return toFantasyTeamMemberViewItems(Stream.of(fantasyTeamMember))
            .findFirst()
            .orElseThrow(() -> new ApiException("This should not happen! Missing the transformed item!"));
  }

  public static List<FantasyTeamMemberViewItem> toFantasyTeamMemberViewItems(List<FantasyTeamMember> fantasyTeamMembers) {
    return toFantasyTeamMemberViewItems(fantasyTeamMembers.stream())
            .collect(toList());
  }

  public static Stream<FantasyTeamMemberViewItem> toFantasyTeamMemberViewItems(Stream<FantasyTeamMember> fantasyTeamMembers) {
    return fantasyTeamMembers
            .map(fantasyTeamMember -> {

              final String series = fantasyTeamMember.getSeries();
              final int season = fantasyTeamMember.getSeason();
              final String username = fantasyTeamMember.getUserName();

              return FantasyTeamMemberViewItem
                      .builder()
                      .id(createFantasyTeamMemberViewItemId(fantasyTeamMember))
                      .teamId(createFantasyTeamViewId(series, season, username))
                      .series(series)
                      .season(season)
                      .round(fantasyTeamMember.getRound())
                      .username(username)
                      .teamMemberTypeId(fantasyTeamMember.getTeamMemberTypeId())
                      .driverId(Optional.ofNullable(fantasyTeamMember.getDriverId())
                              .map(driverId -> toDriverViewItemId(series, season, driverId))
                              .orElse(""))
                      .constructorId(
                              Optional.ofNullable(fantasyTeamMember.getConstructorId())
                              .map(constructorId -> toConstructorViewItemId(series, season, constructorId))
                              .orElse(""))
                      .build();
            });
  }

  private static String createFantasyTeamMemberViewItemId(FantasyTeamMember fantasyTeamMember) {
    return createFantasyTeamMemberViewItemId(createFantasyTeamMemberId(fantasyTeamMember));
  }

  public static FantasyTeamMemberPK createFantasyTeamMemberId(FantasyTeamMember fantasyTeamMember) {
    return FantasyTeamMemberPK.builder()
            .series(fantasyTeamMember.getSeries())
            .season(fantasyTeamMember.getSeason())
            .userName(fantasyTeamMember.getUserName())
            .round(fantasyTeamMember.getRound())
            .teamMemberTypeId(fantasyTeamMember.getTeamMemberTypeId())
            .build();
  }

  private static String createFantasyTeamMemberViewItemId(FantasyTeamMemberPK fantasyTeamMemberId) {
    return "ftm"
            + SEPARATOR + fantasyTeamMemberId.getSeries()
            + SEPARATOR + fantasyTeamMemberId.getSeason()
            + SEPARATOR + fantasyTeamMemberId.getUserName()
            + SEPARATOR + fantasyTeamMemberId.getRound()
            + SEPARATOR + fantasyTeamMemberId.getTeamMemberTypeId();
  }


}
