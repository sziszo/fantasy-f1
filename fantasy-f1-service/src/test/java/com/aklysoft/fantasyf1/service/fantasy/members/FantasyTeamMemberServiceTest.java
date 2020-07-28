package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeam;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorNotExistException;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverNotExistException;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FantasyTeamMemberServiceTest {

  @InjectMocks
  FantasyTeamMemberService fantasyTeamMemberService;

  @Mock
  private FantasyTeamMemberRepository fantasyTeamMemberRepository;

  @Mock
  private OriginalConstructorService originalConstructorService;

  @Mock
  private OriginalDriverService originalDriverService;

  @Mock
  private FantasyDefinitionService fantasyDefinitionService;

  final FantasyTeamPK teamId = FantasyTeamPK.builder()
          .series(FantasySeriesType.FORMULA_1.id)
          .season(2020)
          .userName("user")
          .build();

  final int race = 1;

  final OriginalConstructor mclaren = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("mclaren").name("McLaren").price(1_000_000L).build();
  final OriginalConstructor ferrari = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("ferrari").name("Ferrari").price(2_000_000L).build();
  final OriginalConstructor mercedes = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("mercedes").name("Mercedes").price(2_500_000L).build();
  final OriginalConstructor redbull = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("red_bull").name("Red Bull").price(1_500_000L).build();
  final OriginalConstructor renault = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("renault").name("Renault").price(500_000L).build();
  final OriginalConstructor williams = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("williams").name("Williams").price(300_000L).build();

  final OriginalDriver hamilton = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("hamilton")
          .givenName("Lewis").familyName("Hamilton").constructorId(mercedes.getId()).constructor(mercedes).price(2_000_000L).build();

  final OriginalDriver vettel = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("vettel")
          .givenName("Sebastian").familyName("Vettel").constructorId(ferrari.getId()).constructor(ferrari).price(1_500_000L).build();

  final OriginalDriver sainz = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("sainz")
          .givenName("Carlos").familyName("Sainz").constructorId(mclaren.getId()).constructor(mclaren).price(500_000L).build();


  @Test
  void shouldSetValidConstructor() {
    //given

    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.BODY;
    final String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_1) driver = hamilton;
                      else if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_1 && typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(mclaren);

    //when
    FantasyTeamMember result = fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery);

    //then
    assertEquals(constructorId, result.getConstructorId());
    assertEquals(constructorId, result.getConstructorId());

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(originalConstructorService).getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId);
    verify(originalConstructorService).getConstructor(anyString(), anyInt(), anyString());
  }

  @Test
  void shouldSetValidDriver() {
    //given
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberCategoryType.BODY) constructor = mclaren;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    //when
    FantasyTeamMember result = fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery);

    //then
    assertEquals(driverId, result.getDriverId());
    assertEquals(hamilton, result.getDriver());

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(originalDriverService).getDriver(teamId.getSeries(), teamId.getSeason(), driverId);
    verify(originalDriverService).getDriver(anyString(), anyInt(), anyString());
  }

  @Test
  void shouldSetMyDriver() {
    //given
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberCategoryType.BODY) constructor = mclaren;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    when(fantasyDefinitionService.getNextRace(teamId.getSeries(), teamId.getSeason())).thenReturn(race);

    //when
    FantasyTeamMember result = fantasyTeamMemberService.setTeamMember(teamId, null, modifyFantasyTeamMemberQuery);

    //then
    assertEquals(driverId, result.getDriverId());
    assertEquals(hamilton, result.getDriver());

    verify(fantasyDefinitionService).getNextRace(teamId.getSeries(), teamId.getSeason());
    verify(fantasyDefinitionService).getNextRace(anyString(), anyInt());

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(originalDriverService).getDriver(teamId.getSeries(), teamId.getSeason(), driverId);
    verify(originalDriverService).getDriver(anyString(), anyInt(), anyString());
  }

  private FantasyTeamMemberPK createTeamMemberId(FantasyTeamMemberCategoryType teamMemberTypeId) {
    return FantasyTeamMemberPK
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(teamMemberTypeId)
            .build();
  }

  private FantasyTeamMember createFantasyTeamMember(FantasyTeamMemberCategoryType teamMemberTypeId, boolean constructor) {
    return createFantasyTeamMember(teamMemberTypeId, constructor, 5_000_000L);
  }

  private FantasyTeamMember createFantasyTeamMember(FantasyTeamMemberCategoryType teamMemberTypeId, boolean constructor, long teamMoney) {
    return FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(teamMemberTypeId)
            .teamMemberType(FantasyTeamMemberCategory.builder().id(teamMemberTypeId).isConstructor(constructor).build())
            .team(FantasyTeam.builder().series(teamId.getSeries()).season(teamId.getSeason()).userName(teamId.getUserName()).money(teamMoney).build())
            .build();
  }

  @Test
  void shouldThrowExceptionWhenConstructorIsInValid() {

    //given
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.BODY;
    final String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_1) driver = hamilton;
                      else if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = sainz;

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_1 && typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(mclaren);

    //when & then
    assertThrows(InvalidFantasyTeamMemberException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(originalConstructorService).getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId);
    verify(originalConstructorService).getConstructor(anyString(), anyInt(), anyString());
  }

  @Test
  void shouldThrowExceptionWhenDriverIsInValid() {
    //given
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberCategoryType.BODY) constructor = mercedes;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    //when & then
    assertThrows(InvalidFantasyTeamMemberException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberRepository).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(originalDriverService).getDriver(teamId.getSeries(), teamId.getSeason(), driverId);
    verify(originalDriverService).getDriver(anyString(), anyInt(), anyString());
  }

  @Test
  public void shouldThrowExceptionWhenDriverWasSetAsConstructor() {

    //given
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.ENGINE;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(null);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();

    //when & then
    assertThrows(OriginalConstructorNotExistException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(originalConstructorService).getConstructor(teamId.getSeries(), teamId.getSeason(), driverId);
    verify(originalConstructorService).getConstructor(anyString(), anyInt(), anyString());

    verify(fantasyTeamMemberRepository, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void shouldThrowExceptionWhenConstructorWasSetAsDriver() {

    //given
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.DRIVER_1;
    final String constructorId = mercedes.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(null);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();

    //when & then
    assertThrows(OriginalDriverNotExistException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(originalDriverService).getDriver(teamId.getSeries(), teamId.getSeason(), constructorId);
    verify(originalDriverService).getDriver(anyString(), anyInt(), anyString());

    verify(fantasyTeamMemberRepository, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void shouldThrowExceptionWhenTeamMemberNotExist() {

    FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.BODY;
    String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(null);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();

    //when & then
    assertThrows(FantasyTeamMemberNotExistException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(originalConstructorService, never()).getConstructor(anyString(), anyInt(), anyString());

  }

  @Test
  public void shouldThrowExceptionWhenAddingNewConstructorButTeamDoesNotHaveEnoughMoney() {

    FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.BODY;
    String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true, 500_000L);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_1) driver = hamilton;
                      else if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_1 && typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(mclaren);

    //when & then
    assertThrows(FantasyTeamMemberPriceTooHighException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

  }

  @Test
  public void shouldThrowExceptionWhenAddingNewDriverButTeamDoesNotHaveEnoughMoney() {
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false, 500_000L);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberCategoryType.BODY) constructor = mclaren;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    //when & then
    assertThrows(FantasyTeamMemberPriceTooHighException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

  }


  @Test
  public void shouldThrowExceptionWhenReplacingExistingConstructorButTeamDoesNotHaveEnoughMoney() {

    FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.BODY;
    String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true, 500_000L);
    fantasyTeamMember.setConstructor(williams);
    fantasyTeamMember.setConstructorId(williams.getId());

    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_1) driver = hamilton;
                      else if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_1 && typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(mclaren);

    //when & then
    assertThrows(FantasyTeamMemberPriceTooHighException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

  }

  @Test
  public void shouldThrowExceptionWhenReplacingExistingDriverButTeamDoesNotHaveEnoughMoney() {
    final FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false, 500_000L);
    fantasyTeamMember.setDriverId(sainz.getId());
    fantasyTeamMember.setDriver(sainz);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberCategoryType.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberCategoryType.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberCategoryType.BODY) constructor = mclaren;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberCategoryType.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMemberQuery modifyFantasyTeamMemberQuery = ModifyFantasyTeamMemberQuery.builder()
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    //when & then
    assertThrows(FantasyTeamMemberPriceTooHighException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMemberQuery));

  }

  @Test
  public void testGetFantasyTeamMembers() {

    //given

    final List<FantasyTeamMember> fantasyTeamMembers =
            Stream.of(FantasyTeamMemberCategoryType.values())
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      switch (typeId) {
                        case DRIVER_1:
                          driver = hamilton;
                          break;
                        case DRIVER_2:
                          driver = vettel;
                          break;
                      }

                      OriginalConstructor constructor = null;
                      switch (typeId) {
                        case BODY:
                          constructor = mclaren;
                          break;
                        case ENGINE:
                          constructor = redbull;
                          break;
                        case STAFF:
                          constructor = renault;
                          break;
                      }

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_1 && typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    })
                    .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId)).thenReturn(fantasyTeamMembers);

    //when
    final List<FantasyTeamMember> result = fantasyTeamMemberService.getFantasyTeamMembers(teamId);

    //then
    assertNotNull(result);
    assertEquals(result.size(), FantasyTeamMemberCategoryType.values().length);
    assertEquals(fantasyTeamMembers, result);
  }

  @Test
  void shouldDeleteDriver() {

    //given
    final FantasyTeamMemberCategoryType teamMemberCategoryType = FantasyTeamMemberCategoryType.DRIVER_1;

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberCategoryType);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberCategoryType, false);
    fantasyTeamMember.setDriverId(vettel.getId());

    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);
    when(fantasyTeamMemberRepository.update(fantasyTeamMember)).thenAnswer(invocation -> invocation.getArgument(0));

    final FantasyTeamMember expectedFantasyTeamMember = createFantasyTeamMember(teamMemberCategoryType, false);
    expectedFantasyTeamMember.setDriverId(null);

    //when
    final FantasyTeamMember result = fantasyTeamMemberService.deleteTeamMember(teamId, race, teamMemberCategoryType);

    //then
    assertNotNull(result);
    assertEquals(expectedFantasyTeamMember, result);

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).update(fantasyTeamMember);
    verify(fantasyTeamMemberRepository).update(any(FantasyTeamMember.class));
  }

  @Test
  void shouldDeleteMyDriver() {

    //given
    final FantasyTeamMemberCategoryType teamMemberCategoryType = FantasyTeamMemberCategoryType.DRIVER_1;

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberCategoryType);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberCategoryType, false);
    fantasyTeamMember.setDriverId(hamilton.getId());

    when(fantasyDefinitionService.getNextRace(teamId.getSeries(), teamId.getSeason())).thenReturn(race);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);
    when(fantasyTeamMemberRepository.update(fantasyTeamMember)).thenAnswer(invocation -> invocation.getArgument(0));

    final FantasyTeamMember expectedFantasyTeamMember = createFantasyTeamMember(teamMemberCategoryType, false);
    expectedFantasyTeamMember.setDriverId(null);

    //when
    final FantasyTeamMember result = fantasyTeamMemberService.deleteTeamMember(teamId, null, teamMemberCategoryType);

    //then
    assertNotNull(result);
    assertEquals(expectedFantasyTeamMember, result);

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).update(fantasyTeamMember);
    verify(fantasyTeamMemberRepository).update(any(FantasyTeamMember.class));

  }

  @Test
  void shouldDeleteConstructor() {

    //given
    final FantasyTeamMemberCategoryType teamMemberCategoryType = FantasyTeamMemberCategoryType.BODY;

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberCategoryType);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberCategoryType, true);
    fantasyTeamMember.setConstructorId(mclaren.getId());

    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);
    when(fantasyTeamMemberRepository.update(fantasyTeamMember)).thenAnswer(invocation -> invocation.getArgument(0));

    final FantasyTeamMember expectedFantasyTeamMember = createFantasyTeamMember(teamMemberCategoryType, true);
    expectedFantasyTeamMember.setConstructorId(null);

    //when
    final FantasyTeamMember result = fantasyTeamMemberService.deleteTeamMember(teamId, race, teamMemberCategoryType);

    //then
    assertNotNull(result);
    assertEquals(expectedFantasyTeamMember, result);

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository).update(fantasyTeamMember);
    verify(fantasyTeamMemberRepository).update(any(FantasyTeamMember.class));

  }

  @Test
  public void shouldThrowExceptionOnDeleteTeamMemberWhenTeamMemberNotExist() {

    FantasyTeamMemberCategoryType teamMemberTypeId = FantasyTeamMemberCategoryType.BODY;

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(null);

    //when & then
    assertThrows(FantasyTeamMemberNotExistException.class, () -> fantasyTeamMemberService.deleteTeamMember(teamId, race, teamMemberTypeId));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(fantasyTeamMemberRepository, never()).update(any(FantasyTeamMember.class));
  }

}
