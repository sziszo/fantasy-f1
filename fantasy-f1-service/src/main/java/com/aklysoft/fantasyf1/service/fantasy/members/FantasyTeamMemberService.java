package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorNotExistException;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverNotExistException;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FantasyTeamMemberService {

  private final FantasyTeamMemberRepository fantasyTeamMemberRepository;
  private final OriginalConstructorService originalConstructorService;
  private final OriginalDriverService originalDriverService;

  public FantasyTeamMemberService(FantasyTeamMemberRepository fantasyTeamMemberRepository,
                                  OriginalConstructorService originalConstructorService,
                                  OriginalDriverService originalDriverService) {
    this.fantasyTeamMemberRepository = fantasyTeamMemberRepository;
    this.originalConstructorService = originalConstructorService;
    this.originalDriverService = originalDriverService;
  }


  @Transactional
  public List<FantasyTeamMember> getFantasyTeamMembers(FantasyTeamPK teamId) {
    return fantasyTeamMemberRepository.getFantasyTeamMembers(teamId);
  }

  @Transactional
  public List<FantasyTeamMember> getFantasyTeamMembers(FantasyTeamPK teamId, int race) {
    return fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race);
  }

  @Transactional
  public FantasyTeamMember getFantasyTeamMember(FantasyTeamPK teamId, int race, FantasyTeamMemberTypeId teamMemberTypeId) {
    return fantasyTeamMemberRepository.findById(
            FantasyTeamMemberPK
                    .builder()
                    .series(teamId.getSeries())
                    .season(teamId.getSeason())
                    .userName(teamId.getUserName())
                    .round(race)
                    .teamMemberTypeId(teamMemberTypeId)
                    .build()

    );
  }

  @Transactional
  public FantasyTeamMember setTeamMember(FantasyTeamPK teamId, ModifyFantasyTeamMember modifyFantasyTeamMember) {

    final FantasyTeamMember fantasyTeamMember = getFantasyTeamMember(teamId, modifyFantasyTeamMember.getRace(), modifyFantasyTeamMember.getTeamMemberTypeId());
    if (fantasyTeamMember == null) {
      throw new FantasyTeamMemberNotExistException("FantasyTeamMember does not exist");
    }

    if (fantasyTeamMember.getTeamMemberType().isConstructor()) {
      final OriginalConstructor constructor = originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), modifyFantasyTeamMember.getId());
      if (constructor == null) {
        throw new OriginalConstructorNotExistException(modifyFantasyTeamMember.getId());
      }

      //check driver & constructor rules
      if (!isValidConstructor(teamId, modifyFantasyTeamMember.getRace(), constructor)) {
        throw new InvalidFantasyTeamMemberException(constructor, teamId);
      }

      fantasyTeamMember.setConstructorId(constructor.getId());
      fantasyTeamMember.setConstructor(constructor);
    } else {
      final OriginalDriver driver = originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), modifyFantasyTeamMember.getId());
      if (driver == null) {
        throw new OriginalDriverNotExistException(modifyFantasyTeamMember.getId());
      }

      //check driver & constructor rules
      if (!isValidDriver(teamId, modifyFantasyTeamMember.getRace(), driver)) {
        throw new InvalidFantasyTeamMemberException(driver, teamId);
      }

      fantasyTeamMember.setDriverId(driver.getId());
      fantasyTeamMember.setDriver(driver);
    }

    return fantasyTeamMember;
  }

  private boolean isValidConstructor(FantasyTeamPK teamId, int race, OriginalConstructor constructor) {
    return isValidConstructor(getFantasyTeamMembers(teamId, race), constructor);
  }

  private boolean isValidConstructor(List<FantasyTeamMember> fantasyTeamMembers, OriginalConstructor constructor) {

    return fantasyTeamMembers
            .stream()
            .filter(fantasyTeamMember -> fantasyTeamMember.getDriverId() != null || fantasyTeamMember.getConstructorId() != null)
            .map(fantasyTeamMember -> {
              if (!fantasyTeamMember.getTeamMemberType().isConstructor()) {
                return fantasyTeamMember.getDriver().getConstructor();
              }
              return fantasyTeamMember.getConstructor();
            })
            .noneMatch(originalConstructor -> originalConstructor.equals(constructor));
  }

  private boolean isValidDriver(FantasyTeamPK teamId, int race, OriginalDriver driver) {
    final List<FantasyTeamMember> fantasyTeamMembers = getFantasyTeamMembers(teamId, race);

    boolean isValidDriver = fantasyTeamMembers
            .stream()
            .filter(fantasyTeamMember -> fantasyTeamMember.getDriverId() != null)
            .map(FantasyTeamMember::getDriver)
            .noneMatch(originalDriver -> originalDriver.equals(driver));

    return isValidDriver && isValidConstructor(fantasyTeamMembers, driver.getConstructor());
  }

}