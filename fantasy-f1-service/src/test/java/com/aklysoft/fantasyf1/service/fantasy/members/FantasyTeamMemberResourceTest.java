package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamPK;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.users.UserIdHolder;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberMappers.toFantasyTeamMemberViewItem;
import static com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberMappers.toFantasyTeamMemberViewItems;
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

  @InjectMock
  UserIdHolder userIdHolder;

  final FantasyTeamPK teamId = FantasyTeamPK.builder()
          .series(FantasySeriesType.FORMULA_1.id)
          .season(2020)
          .userName("user")
          .build();

  final int race = 1;

  private final String user1 = "user";
  private final String user2 = "john";
  private final String admin = "admin";

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
            .auth().basic(admin, admin)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/members/{race}/admin/{username}",
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
    assertEquals(FantasyTeamMemberCategoryType.values().length, result.size());

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class));
  }

  @Test
  public void shouldGet403WhenGetOthersFantasyTeamMembersPerRace() {
    given()
            .auth().basic(user1, user1)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/members/{race}/admin/{username}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "username", user2,
                            "race", race))
            .then()
            .statusCode(403);

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class));
  }

  @Test
  public void testGetMyFantasyTeamMembersPerRace() {

    final List<FantasyTeamMember> fantasyTeamMembers = createFantasyTeamMembersStream(race).collect(toList());
    when(fantasyTeamMemberService.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);
    when(userIdHolder.getCurrentUserName()).thenReturn(user1);

    final List<FantasyTeamMemberViewItem> result = given()
            .auth().basic(user1, user1)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/members/{race}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "race", race))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMemberViewItem.class);

    assertNotNull(result);
    assertEquals(FantasyTeamMemberCategoryType.values().length, result.size());
    assertEquals(toFantasyTeamMemberViewItems(fantasyTeamMembers), result);

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(userIdHolder).getCurrentUserName();

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class));
  }

  @Test
  public void testGetOthersFantasyTeamMembersPerRace() {

    final List<FantasyTeamMember> fantasyTeamMembers = createFantasyTeamMembersStream(race).collect(toList());
    when(fantasyTeamMemberService.getFantasyTeamMembers(teamId, race)).thenReturn(fantasyTeamMembers);

    final List<FantasyTeamMemberViewItem> result = given()
            .auth().basic(user2, user2)
            .when()
            .queryParam("username", user1)
            .get("/api/v1/fantasy/{series}/teams/{season}/members/{race}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "race", race))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMemberViewItem.class);

    assertNotNull(result);
    assertEquals(FantasyTeamMemberCategoryType.values().length, result.size());
    assertEquals(toFantasyTeamMemberViewItems(fantasyTeamMembers), result);

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId, race);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());

    verify(userIdHolder, never()).getCurrentUserName();
    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class));
  }

  private Stream<FantasyTeamMember> createFantasyTeamMembersStream(int round) {
    return Stream.of(FantasyTeamMemberCategoryType.values())
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
                      .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(typeId != FantasyTeamMemberCategoryType.DRIVER_1 && typeId != FantasyTeamMemberCategoryType.DRIVER_2).build())
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
            .auth().basic(admin, admin)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/members/admin/{username}",
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
    assertEquals(FantasyTeamMemberCategoryType.values().length * raceCount, result.size());

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class));

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void shouldGet403WhenGetOthersFantasyTeamMembers() {
    given()
            .auth().basic(user1, user1)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/members/admin/{username}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "username", user2))
            .then()
            .statusCode(403);

    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class));
  }

  @Test
  public void testGetMyFantasyTeamMembers() {
    final int raceCount = 2;

    final List<FantasyTeamMember> fantasyTeamMembers =
            IntStream.range(1, raceCount + 1)
                    .boxed()
                    .flatMap(this::createFantasyTeamMembersStream)
                    .collect(toList());


    when(fantasyTeamMemberService.getFantasyTeamMembers(teamId)).thenReturn(fantasyTeamMembers);
    when(userIdHolder.getCurrentUserName()).thenReturn(user1);

    final List<FantasyTeamMemberViewItem> result = given()
            .auth().basic(user1, user1)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/members",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason()))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMemberViewItem.class);

    assertNotNull(result);
    assertEquals(FantasyTeamMemberCategoryType.values().length * raceCount, result.size());
    assertEquals(toFantasyTeamMemberViewItems(fantasyTeamMembers), result);

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class));
    verify(userIdHolder).getCurrentUserName();
    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void testGetOthersFantasyTeamMembers() {
    final int raceCount = 2;

    final List<FantasyTeamMember> fantasyTeamMembers =
            IntStream.range(1, raceCount + 1)
                    .boxed()
                    .flatMap(this::createFantasyTeamMembersStream)
                    .collect(toList());


    when(fantasyTeamMemberService.getFantasyTeamMembers(teamId)).thenReturn(fantasyTeamMembers);

    final List<FantasyTeamMemberViewItem> result = given()
            .auth().basic(user2, user2)
            .when()
            .queryParam("username", user1)
            .get("/api/v1/fantasy/{series}/teams/{season}/members",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason()))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMemberViewItem.class);

    assertNotNull(result);
    assertEquals(FantasyTeamMemberCategoryType.values().length * raceCount, result.size());
    assertEquals(toFantasyTeamMemberViewItems(fantasyTeamMembers), result);

    verify(fantasyTeamMemberService).getFantasyTeamMembers(teamId);
    verify(fantasyTeamMemberService).getFantasyTeamMembers(any(FantasyTeamPK.class));
    verify(userIdHolder, never()).getCurrentUserName();
    verify(fantasyTeamMemberService, never()).getFantasyTeamMembers(any(FantasyTeamPK.class), anyInt());
  }

  @Test
  public void testSetTeamMember() {

    final FantasyTeamMemberCategoryType typeId = FantasyTeamMemberCategoryType.DRIVER_1;

    ModifyFantasyTeamMember modifyFantasyTeamMember =
            ModifyFantasyTeamMember
                    .builder()
                    .teamMemberTypeId(typeId)
                    .id(hamilton.getId())
                    .build();

    final FantasyTeamMember fantasyTeamMember = FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(typeId)
            .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(false).build())
            .driver(hamilton)
            .driverId(hamilton.getId())
            .build();
    when(fantasyTeamMemberService.setTeamMember(teamId, race, modifyFantasyTeamMember))
            .thenReturn(fantasyTeamMember);

    final FantasyTeamMember result = given()
            .auth().basic(admin, admin)
            .contentType(ContentType.JSON)
            .body(modifyFantasyTeamMember)
            .queryParam("race", race)
            .when()
            .post("/api/v1/fantasy/{series}/teams/{season}/members/admin/{username}",
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

    verify(fantasyTeamMemberService).setTeamMember(teamId, race, modifyFantasyTeamMember);
    verify(fantasyTeamMemberService).setTeamMember(any(FantasyTeamPK.class), anyInt(), any(ModifyFantasyTeamMember.class));

  }

  @Test
  public void shouldGet403WhenSetOthersFantasyTeamMember() {
    final FantasyTeamMemberCategoryType typeId = FantasyTeamMemberCategoryType.DRIVER_1;

    ModifyFantasyTeamMember modifyFantasyTeamMember =
            ModifyFantasyTeamMember
                    .builder()
                    .teamMemberTypeId(typeId)
                    .id(hamilton.getId())
                    .build();

    given()
            .auth().basic(user1, user1)
            .contentType(ContentType.JSON)
            .body(modifyFantasyTeamMember)
            .when()
            .post("/api/v1/fantasy/{series}/teams/{season}/members/admin/{username}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "username", user2))
            .then()
            .assertThat()
            .statusCode(403);

    verify(fantasyTeamMemberService, never()).setTeamMember(any(FantasyTeamPK.class), anyInt(), any(ModifyFantasyTeamMember.class));
  }

  @Test
  public void testSetMyTeamMember() {

    final FantasyTeamMemberCategoryType typeId = FantasyTeamMemberCategoryType.DRIVER_1;

    ModifyFantasyTeamMember modifyFantasyTeamMember =
            ModifyFantasyTeamMember
                    .builder()
                    .teamMemberTypeId(typeId)
                    .id(hamilton.getId())
                    .build();

    final FantasyTeamMember fantasyTeamMember = FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(typeId)
            .teamMemberType(FantasyTeamMemberCategory.builder().id(typeId).isConstructor(false).build())
            .driver(hamilton)
            .driverId(hamilton.getId())
            .build();

    when(fantasyTeamMemberService.setTeamMember(teamId, null, modifyFantasyTeamMember)).thenReturn(fantasyTeamMember);
    when(userIdHolder.getCurrentUserName()).thenReturn(user1);

    final FantasyTeamMemberViewItem result = given()
            .auth().basic(user1, user1)
            .contentType(ContentType.JSON)
            .body(modifyFantasyTeamMember)
            .when()
            .post("/api/v1/fantasy/{series}/teams/{season}/members",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason()))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeamMemberViewItem.class);

    assertNotNull(result);
    assertEquals(toFantasyTeamMemberViewItem(fantasyTeamMember), result);

    verify(fantasyTeamMemberService).setTeamMember(teamId, null, modifyFantasyTeamMember);
//    verify(fantasyTeamMemberService).setTeamMember(any(FantasyTeamPK.class), null, any(ModifyFantasyTeamMember.class));
    verify(userIdHolder).getCurrentUserName();

  }

  @Test
  public void testDeleteMyTeamMember() {

    final FantasyTeamMemberCategoryType teamMemberCategoryType = FantasyTeamMemberCategoryType.DRIVER_1;

    final FantasyTeamMember fantasyTeamMember = FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(teamMemberCategoryType)
            .teamMemberType(FantasyTeamMemberCategory.builder().id(teamMemberCategoryType).isConstructor(false).build())
            .driver(hamilton)
            .driverId(hamilton.getId())
            .build();

    when(fantasyTeamMemberService.deleteTeamMember(teamId, null, teamMemberCategoryType)).thenReturn(fantasyTeamMember);
    when(userIdHolder.getCurrentUserName()).thenReturn(user1);

    final FantasyTeamMemberViewItem result = given()
            .auth().basic(user1, user1)
            .when()
            .delete("/api/v1/fantasy/{series}/teams/{season}/members/{teamMemberCategoryType}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "teamMemberCategoryType", teamMemberCategoryType))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeamMemberViewItem.class);

    assertNotNull(result);
    assertEquals(toFantasyTeamMemberViewItem(fantasyTeamMember), result);

    verify(fantasyTeamMemberService).deleteTeamMember(teamId, null, teamMemberCategoryType);
    verify(userIdHolder).getCurrentUserName();

  }

  @Test
  public void testDeleteTeamMember() {

    final FantasyTeamMemberCategoryType teamMemberCategoryType = FantasyTeamMemberCategoryType.DRIVER_1;

    final FantasyTeamMember fantasyTeamMember = FantasyTeamMember
            .builder()
            .series(teamId.getSeries())
            .season(teamId.getSeason())
            .userName(teamId.getUserName())
            .round(race)
            .teamMemberTypeId(teamMemberCategoryType)
            .teamMemberType(FantasyTeamMemberCategory.builder().id(teamMemberCategoryType).isConstructor(false).build())
            .driver(hamilton)
            .driverId(hamilton.getId())
            .build();
    when(fantasyTeamMemberService.deleteTeamMember(teamId, race, teamMemberCategoryType)).thenReturn(fantasyTeamMember);

    final FantasyTeamMember result = given()
            .auth().basic(admin, admin)
            .queryParam("race", race)
            .when()
            .delete("/api/v1/fantasy/{series}/teams/{season}/members/{teamMemberCategoryType}/admin/{username}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "teamMemberCategoryType", teamMemberCategoryType,
                            "username", teamId.getUserName()))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeamMember.class);

    assertNotNull(result);
    assertEquals(fantasyTeamMember, result);

    verify(fantasyTeamMemberService).deleteTeamMember(teamId, race, teamMemberCategoryType);
    verify(fantasyTeamMemberService).deleteTeamMember(any(FantasyTeamPK.class), anyInt(), any(FantasyTeamMemberCategoryType.class));
  }

  @Test
  public void shouldGet403WhenDeleteOthersFantasyTeamMember() {
    final FantasyTeamMemberCategoryType teamMemberCategoryType = FantasyTeamMemberCategoryType.DRIVER_1;

    given()
            .auth().basic(user1, user1)
            .when()
            .delete("/api/v1/fantasy/{series}/teams/{season}/members/{teamMemberCategoryType}/admin/{username}",
                    Map.of("series", teamId.getSeries(),
                            "season", teamId.getSeason(),
                            "teamMemberCategoryType", teamMemberCategoryType,
                            "username", teamId.getUserName()))
            .then()
            .assertThat()
            .statusCode(403);

    verify(fantasyTeamMemberService, never()).deleteTeamMember(any(FantasyTeamPK.class), anyInt(), any(FantasyTeamMemberCategoryType.class));
  }
}
