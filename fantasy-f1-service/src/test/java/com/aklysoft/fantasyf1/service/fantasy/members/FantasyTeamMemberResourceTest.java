package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@QuarkusTest
@Tag("integration")
class FantasyTeamMemberResourceTest {

  @InjectMock
  FantasyTeamMemberService fantasyTeamMemberService;

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

  @Test
  public void testGetFantasyTeamMembersPerRace() {

    final List<FantasyTeamMember> fantasyTeamMembers = createFantasyTeamMembersStream(race).collect(toList());
    when(fantasyTeamMemberService.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    final List<FantasyTeamMember> result = given()
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/{username}/members/{race}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "username", teamId.getUserName(),
                            "race", race))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMember.class);

    assertNotNull(result);
    assertEquals(FantasyTeamMemberTypeId.values().length, result.size());

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class));
  }

  private Stream<FantasyTeamMember> createFantasyTeamMembersStream(int round) {
    return Stream.of(FantasyTeamMemberTypeId.values())
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
                      .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberTypeId.DRIVER_1 && typeId != FantasyTeamMemberTypeId.DRIVER_2).build())
                      .driver(driver)
                      .driverId(driver == null ? null : driver.getId())
                      .constructor(constructor)
                      .constructorId(constructor == null ? null : constructor.getId())
                      .build();
            });
  }

  @Test
  public void testGetFantasyTeamMembers() {
    final int raceCount = 2;

    final List<FantasyTeamMember> fantasyTeamMembers =
            IntStream.range(1, raceCount + 1)
                    .boxed()
                    .flatMap(this::createFantasyTeamMembersStream)
                    .collect(toList());


    when(fantasyTeamMemberService.getFantasyTeamMembers(teamId)).thenReturn(fantasyTeamMembers);

    final List<FantasyTeamMember> result = given()
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/{username}/members",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "username", teamId.getUserName()))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMember.class);

    assertNotNull(result);
    assertEquals(FantasyTeamMemberTypeId.values().length * raceCount, result.size());

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class));

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void testSetTeamMember() {

    final FantasyTeamMemberTypeId typeId = FantasyTeamMemberTypeId.DRIVER_1;

    ModifyFantasyTeamMember modifyFantasyTeamMember =
            ModifyFantasyTeamMember
                    .builder()
                    .teamMemberTypeId(typeId)
                    .race(race)
                    .id(hamilton.getId())
                    .build();

    final FantasyTeamMember fantasyTeamMember = FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(typeId)
            .teamMemberType(FantasyTeamMemberType.builder().id(typeId).isConstructor(false).build())
            .driver(hamilton)
            .driverId(hamilton.getId())
            .build();
    when(fantasyTeamMemberService.setTeamMember(teamId, modifyFantasyTeamMember))
            .thenReturn(
                    fantasyTeamMember
            );

    final FantasyTeamMember result = given()
            .contentType(ContentType.JSON)
            .body(modifyFantasyTeamMember)
            .when()
            .post("/api/v1/fantasy/{series}/teams/{season}/{username}/members",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "username", teamId.getUserName()))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeamMember.class);

    assertNotNull(result);
    assertEquals(fantasyTeamMember, result);
  }

}
