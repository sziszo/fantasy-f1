package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeam;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.races.OriginalRace;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Tag("integration")
class FantasyTeamMemberRepositoryImplTest {

  @Inject
  FantasyTeamMemberRepositoryImpl fantasyTeamMemberRepositoryImpl;

  @PersistenceContext
  EntityManager em;

  final FantasyTeamPK teamId = FantasyTeamPK.builder()
          .series(FantasySeriesType.FORMULA_1.id)
          .season(2020)
          .userName("user")
          .build();

  int raceCount = 2;

  final OriginalConstructor mclaren = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("mclaren").name("McLaren").build();
  final OriginalConstructor ferrari = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("ferrari").name("Ferrari").build();
  final OriginalConstructor mercedes = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("mercedes").name("Mercedes").build();
  final OriginalConstructor redbull = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("red_bull").name("Red Bull").build();
  final OriginalConstructor renault = OriginalConstructor.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("renault").name("Renault").build();

  final OriginalDriver hamilton = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("hamilton")
          .givenName("Lewis").familyName("Hamilton").constructorId(mercedes.getId()).constructor(mercedes).build();

  final OriginalDriver vettel = OriginalDriver.builder().series(teamId.getSeries()).season(teamId.getSeason()).id("vettel")
          .givenName("Carlos").familyName("Sainz").constructorId(ferrari.getId()).constructor(ferrari).build();

  @BeforeEach
  @Transactional
  public void insertTestEntities() {

    em.createQuery("delete from FantasyTeamMember").executeUpdate();
    em.createQuery("delete from FantasyTeam").executeUpdate();
    em.createQuery("delete from OriginalRace").executeUpdate();
    em.createQuery("delete from OriginalDriver").executeUpdate();
    em.createQuery("delete from OriginalConstructor").executeUpdate();


    Stream.of(mclaren, ferrari, mercedes, redbull, renault)
            .forEach(em::persist);

    Stream.of(hamilton, vettel)
            .forEach(em::persist);

    em.persist(FantasyTeam.builder().series(teamId.getSeries()).season(teamId.getSeason()).userName(teamId.getUserName()).build());

    IntStream.range(1, 1 + raceCount)
            .boxed()
            .map(round -> OriginalRace.builder().series(teamId.getSeries()).season(teamId.getSeason()).round(round).raceName("TestRace-" + round).build())
            .forEach(em::persist);

    IntStream.range(1, 1 + raceCount)
            .boxed()
            .flatMap(round ->
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
                                      .round(round)
                                      .teamMemberTypeId(typeId)
                                      .driver(driver)
                                      .driverId(driver == null ? null : driver.getId())
                                      .constructor(constructor)
                                      .constructorId(constructor == null ? null : constructor.getId())
                                      .build();
                            }))
            .forEach(em::persist);

  }

  @Test
  @Transactional
  public void shouldGetFantasyTeamMembers() {

    //when
    final List<FantasyTeamMember> result = fantasyTeamMemberRepositoryImpl.getFantasyTeamMembers(teamId);

    //then
    assertNotNull(result);
    assertEquals(FantasyTeamMemberCategoryType.values().length * raceCount, result.size());
  }

  @Test
  @Transactional
  public void shouldGetFantasyTeamMembersPerRace() {

    //given
    final int race = 1;

    //when
    final List<FantasyTeamMember> result = fantasyTeamMemberRepositoryImpl.getFantasyTeamMembers(teamId, race);

    //then
    assertNotNull(result);
    assertEquals(FantasyTeamMemberCategoryType.values().length, result.size());
  }


}
