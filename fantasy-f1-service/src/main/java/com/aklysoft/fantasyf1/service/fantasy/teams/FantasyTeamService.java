package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.core.utils.DateUtils;
import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMember;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberCategoryType;
import com.aklysoft.fantasyf1.service.players.PlayerService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class FantasyTeamService {

  private final FantasyTeamRepository fantasyTeamRepository;
  private final PlayerService playerService;
  private final FantasyDefinitionService fantasyDefinitionService;

  public FantasyTeamService(FantasyTeamRepository fantasyTeamRepository, PlayerService playerService,
                            FantasyDefinitionService fantasyDefinitionService) {
    this.fantasyTeamRepository = fantasyTeamRepository;
    this.playerService = playerService;
    this.fantasyDefinitionService = fantasyDefinitionService;
  }

  @Transactional
  public List<FantasyTeam> getAllFantasyTeams(String series, int season) {
    return fantasyTeamRepository.findAllBySeriesAndSeason(series, season);
  }

  @Transactional
  public FantasyTeam getFantasyTeam(String series, int season, String userName) {
    return fantasyTeamRepository.findById(
            FantasyTeamPK
                    .builder()
                    .series(series)
                    .season(season)
                    .userName(userName)
                    .build()
    );
  }

  @Transactional
  public FantasyTeam createFantasyTeam(NewFantasyTeam newFantasyTeam) {
    final String series = newFantasyTeam.getSeries();
    final String username = newFantasyTeam.getUsername();
    final String teamName = newFantasyTeam.getName();

    final int season = Optional.ofNullable(newFantasyTeam.getSeason()).orElse(fantasyDefinitionService.getCurrentSeason(series));

    if (fantasyTeamRepository.isExistName(teamName, username)) {
      throw new FantasyTeamNameExistException(teamName);
    }

    FantasyTeam fantasyTeam = getFantasyTeam(series, season, username);
    if (fantasyTeam != null) {
      throw new FantasyTeamExistException(teamName);
    }

    int round = fantasyDefinitionService.getNextRace(series, season);

    final List<FantasyTeamMember> teamMembers = Stream.of(FantasyTeamMemberCategoryType.values())
            .map(teamMemberTypeId ->
                    FantasyTeamMember
                            .builder()
                            .series(series)
                            .season(season)
                            .userName(username)
                            .round(round)
                            .teamMemberTypeId(teamMemberTypeId)
                            .build())
            .collect(Collectors.toList());

    fantasyTeam = FantasyTeam
            .builder()
            .userName(username)
            .series(series)
            .season(season)
            .name(teamName)
            .money(fantasyDefinitionService.getInitialMoney(series, season))
            .teamMembers(teamMembers)
            .player(playerService.getPlayer(username))
            .creator(newFantasyTeam.getCreator())
            .created(DateUtils.getUtcNow())
            .build();

    this.fantasyTeamRepository.persist(fantasyTeam);
    return fantasyTeam;
  }

  @Transactional
  public FantasyTeam updateFantasyTeam(ModifyFantasyTeam modifyFantasyTeam) {

    if (fantasyTeamRepository.isExistName(modifyFantasyTeam.getName(), modifyFantasyTeam.getUsername())) {
      throw new FantasyTeamNameExistException(modifyFantasyTeam.getName());
    }

    final int season = Optional.ofNullable(modifyFantasyTeam.getSeason()).orElse(fantasyDefinitionService.getCurrentSeason(modifyFantasyTeam.getSeries()));
    final FantasyTeam fantasyTeam = getFantasyTeam(modifyFantasyTeam.getSeries(), season, modifyFantasyTeam.getUsername());
    if (fantasyTeam == null) {
      throw new FantasyTeamNotExistException(modifyFantasyTeam.getName());
    }

    fantasyTeam.setName(modifyFantasyTeam.getName());
    fantasyTeam.setModifier(modifyFantasyTeam.getModifier());
    fantasyTeam.setModified(DateUtils.getUtcNow());
    return fantasyTeam;
  }

}

