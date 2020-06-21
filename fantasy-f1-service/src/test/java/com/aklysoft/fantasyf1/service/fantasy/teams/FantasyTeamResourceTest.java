package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamMappers.toFantasyTeamViewItem;
import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
@Tag("integration")
class FantasyTeamResourceTest {

  @InjectMock
  FantasyTeamService fantasyTeamService;

  private final String series = FantasySeriesType.FORMULA_1.id;
  private final int season = 2019;

  private final String user1 = "user";
  private final String user2 = "john";
  private final String admin = "admin";

  @Test
  public void shouldGetAllFantasyTeams() {

    final List<FantasyTeam> fantasyTeams =
            Map.of(user1, "UserF1Team", user2, "JohnF1Team")
                    .entrySet()
                    .stream()
                    .map(entry -> FantasyTeam.builder().series(series).season(season).userName(entry.getKey()).name(entry.getValue()).build())
                    .collect(toList());

    when(fantasyTeamService.getAllFantasyTeams(series, season)).thenReturn(fantasyTeams);

    //when
    final List<FantasyTeamViewItem> result = given()
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/all", Map.of("series", series, "season", season))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamViewItem.class);

    assertNotNull(result);
    assertEquals(toFantasyTeamViewItem(fantasyTeams), result);

    verify(fantasyTeamService).getAllFantasyTeams(series, season);
  }

  @Test
  public void shouldGetMineFantasyTeam() {

    final FantasyTeam fantasyTeam = FantasyTeam.builder().series(series).season(season).userName(user1).name("User1F1Team").build();
    when(fantasyTeamService.getFantasyTeam(series, season, user1)).thenReturn(fantasyTeam);

    //when
    final FantasyTeamViewItem result = given()
            .auth().basic(user1, user1)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}", Map.of("series", series, "season", season))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeamViewItem.class);

    assertNotNull(result);
    assertEquals(toFantasyTeamViewItem(fantasyTeam), result);

    verify(fantasyTeamService).getFantasyTeam(series, season, user1);
  }

  @Test
  public void shouldGetOthersFantasyTeamByAdmin() {

    final FantasyTeam fantasyTeam = FantasyTeam.builder().series(series).season(season).userName(user1).name("User1F1Team").build();
    when(fantasyTeamService.getFantasyTeam(series, season, user1)).thenReturn(fantasyTeam);

    //when
    final FantasyTeam result = given()
            .auth().basic(admin, admin)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/admin/{username}",
                    Map.of("series", series, "season", season, "username", user1))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeam.class);

    assertNotNull(result);
    assertEquals(fantasyTeam, result);

    verify(fantasyTeamService).getFantasyTeam(series, season, user1);
  }

  @Test
  public void shouldGet403WhenUserGetOthersFantasyTeam() {

    given()
            .auth().basic(user1, user1)
            .when()
            .get("/api/v1/fantasy/{series}/teams/{season}/admin/{username}",
                    Map.of("series", series, "season", season, "username", user2))
            .then()
            .statusCode(403);

    verify(fantasyTeamService, never()).getFantasyTeam(anyString(), anyInt(), anyString());
  }

  @Test
  public void shouldCreateFantasyTeam() {
    String teamName = "F1TeamName";

    final NewFantasyTeam newFantasyTeam =
            NewFantasyTeam.builder()
                    .series(series)
                    .username(user1)
                    .name(teamName)
                    .creator(user1)
                    .build();

    final FantasyTeam fantasyTeam =
            FantasyTeam.builder()
                    .series(series)
                    .season(season)
                    .userName(user1)
                    .name(teamName)
                    .creator(user1)
                    .build();

    when(fantasyTeamService.createFantasyTeam(newFantasyTeam)).thenReturn(fantasyTeam);


    final FantasyTeam result = given()
            .auth().basic(user1, user1)
            .contentType(ContentType.JSON)
            .body(teamName)
            .when()
            .put("/api/v1/fantasy/{series}/teams/", Map.of("series", this.series))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeam.class);

    assertNotNull(result);
    assertEquals(fantasyTeam, result);

    verify(fantasyTeamService).createFantasyTeam(newFantasyTeam);
    verify(fantasyTeamService).createFantasyTeam(any(NewFantasyTeam.class));

  }

  @Test
  public void shouldCreateFantasyTeamByAdmin() {
    String teamName = "F1TeamName";

    final NewFantasyTeam newFantasyTeam =
            NewFantasyTeam.builder()
                    .series(series)
                    .season(season)
                    .username(user1)
                    .name(teamName)
                    .creator(admin)
                    .build();

    final FantasyTeam fantasyTeam =
            FantasyTeam.builder()
                    .series(series)
                    .season(season)
                    .userName(user1)
                    .name(teamName)
                    .creator(admin)
                    .build();

    when(fantasyTeamService.createFantasyTeam(newFantasyTeam)).thenReturn(fantasyTeam);


    final FantasyTeam result = given()
            .auth().basic(admin, admin)
            .contentType(ContentType.JSON)
            .body(teamName)
            .when()
            .put("/api/v1/fantasy/{series}/teams/{season}/admin/{username}",
                    Map.of("series", this.series, "season", season, "username", user1))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeam.class);

    assertNotNull(result);
    assertEquals(fantasyTeam, result);

    verify(fantasyTeamService).createFantasyTeam(newFantasyTeam);
    verify(fantasyTeamService).createFantasyTeam(any(NewFantasyTeam.class));

  }

  @Test
  public void shouldGet403WhenUserCreateFantasyTeamForOthers() {
    String teamName = "F1TeamName";

    given()
            .auth().basic(user1, user1)
            .contentType(ContentType.JSON)
            .body(teamName)
            .when()
            .put("/api/v1/fantasy/{series}/teams/{season}/admin/{username}",
                    Map.of("series", this.series, "season", season, "username", user2))
            .then()
            .assertThat()
            .statusCode(403);
  }

  @Test
  public void shouldUpdateFantasyTeam() {

    String teamName = "NewF1TeamName";

    final ModifyFantasyTeam modifyFantasyTeam =
            ModifyFantasyTeam.builder()
                    .series(series)
                    .username(user1)
                    .name(teamName)
                    .modifier(user1)
                    .build();

    final FantasyTeam fantasyTeam =
            FantasyTeam.builder()
                    .series(series)
                    .season(season)
                    .userName(user1)
                    .name(teamName)
                    .modifier(user1)
                    .build();

    when(fantasyTeamService.updateFantasyTeam(modifyFantasyTeam)).thenReturn(fantasyTeam);


    final FantasyTeam result = given()
            .auth().basic(user1, user1)
            .contentType(ContentType.JSON)
            .body(teamName)
            .when()
            .post("/api/v1/fantasy/{series}/teams/", Map.of("series", this.series))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeam.class);

    assertNotNull(result);
    assertEquals(fantasyTeam, result);

    verify(fantasyTeamService).updateFantasyTeam(modifyFantasyTeam);
    verify(fantasyTeamService).updateFantasyTeam(any(ModifyFantasyTeam.class));

  }

  @Test
  public void shouldUpdateFantasyTeamByAdmin() {

    String teamName = "NewF1TeamName";

    final ModifyFantasyTeam modifyFantasyTeam =
            ModifyFantasyTeam.builder()
                    .series(series)
                    .season(season)
                    .username(user1)
                    .name(teamName)
                    .modifier(admin)
                    .build();

    final FantasyTeam fantasyTeam =
            FantasyTeam.builder()
                    .series(series)
                    .season(season)
                    .userName(user1)
                    .name(teamName)
                    .modifier(admin)
                    .build();

    when(fantasyTeamService.updateFantasyTeam(modifyFantasyTeam)).thenReturn(fantasyTeam);

    final FantasyTeam result = given()
            .auth().basic(admin, admin)
            .contentType(ContentType.JSON)
            .body(teamName)
            .when()
            .post("/api/v1/fantasy/{series}/teams/{season}/admin/{username}",
                    Map.of("series", this.series, "season", season, "username", user1))
            .then()
            .assertThat()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyTeam.class);

    assertNotNull(result);
    assertEquals(fantasyTeam, result);

    verify(fantasyTeamService).updateFantasyTeam(modifyFantasyTeam);
    verify(fantasyTeamService).updateFantasyTeam(any(ModifyFantasyTeam.class));

  }

  @Test
  public void shouldGet403WhenUserUpdateFantasyTeamForOthers() {
    String teamName = "NewF1TeamName";

    given()
            .auth().basic(user1, user1)
            .contentType(ContentType.JSON)
            .body(teamName)
            .when()
            .post("/api/v1/fantasy/{series}/teams/{season}/admin/{username}",
                    Map.of("series", this.series, "season", season, "username", user2))
            .then()
            .assertThat()
            .statusCode(403);
  }

}
