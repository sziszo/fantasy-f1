package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeam;
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
  private final FantasyDefinitionService fantasyDefinitionService;

  public FantasyTeamMemberService(FantasyTeamMemberRepository fantasyTeamMemberRepository,
                                  OriginalConstructorService originalConstructorService,
                                  OriginalDriverService originalDriverService,
                                  FantasyDefinitionService fantasyDefinitionService) {
    this.fantasyTeamMemberRepository = fantasyTeamMemberRepository;
    this.originalConstructorService = originalConstructorService;
    this.originalDriverService = originalDriverService;
    this.fantasyDefinitionService = fantasyDefinitionService;
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
  public FantasyTeamMember getFantasyTeamMember(FantasyTeamPK teamId, int race, FantasyTeamMemberCategoryType teamMemberTypeId) {
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
  public FantasyTeamMember setTeamMember(FantasyTeamPK teamId, Integer race, ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery) {
    if (race == null) {
      race = fantasyDefinitionService.getNextRace(teamId.getSeries(), teamId.getSeason());
    }

    final FantasyTeamMember fantasyTeamMember = getFantasyTeamMember(teamId, race, modifyFantasyTeamMemberQuery.getTeamMemberTypeId());
    if (fantasyTeamMember == null) {
      throw new FantasyTeamMemberNotExistException(modifyFantasyTeamMemberQuery.getTeamMemberTypeId());
    }

    if (fantasyTeamMember.getTeamMemberType().isConstructor()) {
      final OriginalConstructor constructor = originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), modifyFantasyTeamMemberQuery.getId());
      if (constructor == null) {
        throw new OriginalConstructorNotExistException(modifyFantasyTeamMemberQuery.getId());
      }

      //check driver & constructor rules
      if (!isValidConstructor(teamId, race, constructor)) {
        throw new InvalidFantasyTeamMemberException(constructor, teamId);
      }

      updateTeamMoney(fantasyTeamMember, fantasyTeamMember.getConstructor(), constructor);

      fantasyTeamMember.setConstructorId(constructor.getId());
      fantasyTeamMember.setConstructor(constructor);

    } else {
      final OriginalDriver driver = originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), modifyFantasyTeamMemberQuery.getId());
      if (driver == null) {
        throw new OriginalDriverNotExistException(modifyFantasyTeamMemberQuery.getId());
      }

      //check driver & constructor rules
      if (!isValidDriver(teamId, race, driver)) {
        throw new InvalidFantasyTeamMemberException(driver, teamId);
      }

      updateTeamMoney(fantasyTeamMember, fantasyTeamMember.getDriver(), driver);

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

  private void updateTeamMoney(FantasyTeamMember fantasyTeamMember, FantasyTeamMemberPriceItem currentFantasyTeamMemberPriceItem,
                               FantasyTeamMemberPriceItem newFantasyTeamMemberPriceItem) {

    final FantasyTeam fantasyTeam = fantasyTeamMember.getTeam();
    long money = fantasyTeam.getMoney();
    if (currentFantasyTeamMemberPriceItem != null) {
      money += currentFantasyTeamMemberPriceItem.getPrice();
    }

    if (newFantasyTeamMemberPriceItem != null) {
      if (money < newFantasyTeamMemberPriceItem.getPrice()) {
        throw new FantasyTeamMemberPriceTooHighException(newFantasyTeamMemberPriceItem);
      }
      money -= newFantasyTeamMemberPriceItem.getPrice();
    }

    fantasyTeam.setMoney(money);
  }


  @Transactional
  public FantasyTeamMember deleteTeamMember(FantasyTeamPK teamId, Integer race, FantasyTeamMemberCategoryType teamMemberCategoryType) {

    if (race == null) {
      race = fantasyDefinitionService.getNextRace(teamId.getSeries(), teamId.getSeason());
    }

    final FantasyTeamMember fantasyTeamMember = getFantasyTeamMember(teamId, race, teamMemberCategoryType);
    if (fantasyTeamMember == null) {
      throw new FantasyTeamMemberNotExistException(teamMemberCategoryType);
    }

    if (fantasyTeamMember.getTeamMemberType().isConstructor()) {
      updateTeamMoney(fantasyTeamMember, fantasyTeamMember.getConstructor(), null);
      fantasyTeamMember.setConstructorId(null);
    } else {
      updateTeamMoney(fantasyTeamMember, fantasyTeamMember.getDriver(), null);
      fantasyTeamMember.setDriverId(null);
    }

    return fantasyTeamMemberRepository.update(fantasyTeamMember);
  }


}
