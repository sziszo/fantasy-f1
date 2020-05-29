package com.aklysoft.fantasyf1.service.fantasy.members;

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

  final FantasyTeamPK teamId = FantasyTeamPK.builder()
          .series("f1")
          .season(2020)
          .userName("user")
          .build();

  final int race = 1;

  final OriginalConstructor mclaren = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("mclaren").name("McLaren").build();
  final OriginalConstructor ferrari = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("ferrari").name("Ferrari").build();
  final OriginalConstructor mercedes = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("mercedes").name("Mercedes").build();
  final OriginalConstructor redbull = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("red_bull").name("Red Bull").build();
  final OriginalConstructor renault = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("renault").name("Renault").build();

  final OriginalDriver hamilton = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("hamilton")
          .givenName("Lewis").familyName("Hamilton").constructorId(mercedes.getId()).constructor(mercedes).build();

  final OriginalDriver vettel = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("vettel")
          .givenName("Carlos").familyName("Sainz").constructorId(ferrari.getId()).constructor(ferrari).build();

  final OriginalDriver sainz = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("vettel")
          .givenName("Carlos").familyName("Sainz").constructorId(mclaren.getId()).constructor(mclaren).build();


  @Test
  void shouldSetValidConstructor() {
    //given

    final FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.BODY;
    final String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberTypeId.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberTypeId.DRIVER_1) driver = hamilton;
                      else if (typeId == FantasyTeamMemberTypeId.DRIVER_2) driver = vettel;

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberTypeId.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberTypeId.STAFF) constructor = renault;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberTypeId.DRIVER_1 && typeId != FantasyTeamMemberTypeId.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(mclaren);

    //when
    FantasyTeamMember result = fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember);

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
    final FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberTypeId.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberTypeId.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberTypeId.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberTypeId.BODY) constructor = mclaren;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberTypeId.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberTypeId.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    //when
    FantasyTeamMember result = fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember);

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

  private FantasyTeamMemberPK createTeamMemberId(FantasyTeamMemberTypeId teamMemberTypeId) {
    return FantasyTeamMemberPK
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(teamMemberTypeId)
            .build();
  }

  private FantasyTeamMember createFantasyTeamMember(FantasyTeamMemberTypeId teamMemberTypeId, boolean constructor) {
    return FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(teamMemberTypeId)
            .teamMemberType(FantasyTeamMemberType.builder().id(teamMemberTypeId).isConstructor(constructor).build())
            .build();
  }

  @Test
  void shouldThrowExceptionWhenConstructorIsInValid() {

    //given
    final FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.BODY;
    final String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);


    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberTypeId.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberTypeId.DRIVER_1) driver = hamilton;
                      else if (typeId == FantasyTeamMemberTypeId.DRIVER_2) driver = sainz;

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberTypeId.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberTypeId.STAFF) constructor = renault;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberTypeId.DRIVER_1 && typeId != FantasyTeamMemberTypeId.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(mclaren);

    //when & then
    assertThrows(InvalidFantasyTeamMemberException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember));

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
    final FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.DRIVER_1;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    final List<FantasyTeamMember> fantasyTeamMembers = Stream.concat(
            Stream.of(fantasyTeamMember),
            Stream.of(FantasyTeamMemberTypeId.values())
                    .filter(typeId -> !typeId.equals(teamMemberTypeId))
                    .map(typeId -> {

                      OriginalConstructor constructor = null;
                      if (typeId == FantasyTeamMemberTypeId.ENGINE) constructor = redbull;
                      else if (typeId == FantasyTeamMemberTypeId.STAFF) constructor = renault;
                      else if (typeId == FantasyTeamMemberTypeId.BODY) constructor = mercedes;

                      OriginalDriver driver = null;
                      if (typeId == FantasyTeamMemberTypeId.DRIVER_2) driver = vettel;

                      return FantasyTeamMember
                              .builder()
                              .series(teamId.getSeries())
                              .season(teamId.getSeason())
                              .userName(teamId.getUserName())
                              .round(race)
                              .teamMemberTypeId(typeId)
                              .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberTypeId.DRIVER_2).build())
                              .driver(driver)
                              .driverId(driver == null ? null : driver.getId())
                              .constructor(constructor)
                              .constructorId(constructor == null ? null : constructor.getId())
                              .build();
                    }))
            .collect(Collectors.toList());

    when(fantasyTeamMemberRepository.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();


    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(hamilton);

    //when & then
    assertThrows(InvalidFantasyTeamMemberException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember));

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
    final FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.ENGINE;
    final String driverId = hamilton.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, true);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    when(originalConstructorService.getConstructor(teamId.getSeries(), teamId.getSeason(), driverId)).thenReturn(null);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(driverId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();

    //when & then
    assertThrows(OriginalConstructorNotExistException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(originalConstructorService).getConstructor(teamId.getSeries(), teamId.getSeason(), driverId);
    verify(originalConstructorService).getConstructor(anyString(), anyInt(), anyString());

    verify(fantasyTeamMemberRepository, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void shouldThrowExceptionWhenConstructorWasSetAsDriver() {

    //given
    final FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.DRIVER_1;
    final String constructorId = mercedes.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    final FantasyTeamMember fantasyTeamMember = createFantasyTeamMember(teamMemberTypeId, false);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(fantasyTeamMember);

    when(originalDriverService.getDriver(teamId.getSeries(), teamId.getSeason(), constructorId)).thenReturn(null);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();

    //when & then
    assertThrows(OriginalDriverNotExistException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(originalDriverService).getDriver(teamId.getSeries(), teamId.getSeason(), constructorId);
    verify(originalDriverService).getDriver(anyString(), anyInt(), anyString());

    verify(fantasyTeamMemberRepository, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void shouldThrowExceptionWhenTeamMemberNotExist() {

    FantasyTeamMemberTypeId teamMemberTypeId = FantasyTeamMemberTypeId.BODY;
    String constructorId = mclaren.getId();

    final FantasyTeamMemberPK fantasyTeamMemberId = createTeamMemberId(teamMemberTypeId);
    when(fantasyTeamMemberRepository.findById(fantasyTeamMemberId)).thenReturn(null);

    ModifyFantasyTeamMember modifyFantasyTeamMember = ModifyFantasyTeamMember.builder()
            .race(race)
            .id(constructorId)
            .teamMemberTypeId(teamMemberTypeId)
            .build();

    //when & then
    assertThrows(FantasyTeamMemberNotExistException.class, () -> fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember));

    verify(fantasyTeamMemberRepository).findById(fantasyTeamMemberId);
    verify(fantasyTeamMemberRepository).findById(any(FantasyTeamMemberPK.class));

    verify(originalConstructorService, never()).getConstructor(anyString(), anyInt(), anyString());

  }

  @Test
  public void testGetFantasyTeamMembers() {

    //given

    final List<FantasyTeamMember> fantasyTeamMembers =
            Stream.of(FantasyTeamMemberTypeId.values())
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
                              .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberTypeId.DRIVER_1 && typeId != FantasyTeamMemberTypeId.DRIVER_2).build())
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
    assertEquals(result.size(), FantasyTeamMemberTypeId.values().length);
    assertEquals(fantasyTeamMembers, result);
  }

}
