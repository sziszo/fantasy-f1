package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeries;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesService;
import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeriesType;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberCategory;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberCategoryService;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberCategoryType;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberViewItem;
import com.aklysoft.fantasyf1.service.original.races.OriginalRace;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionMappers.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Tag("integration")
class FantasyDefinitionsResourceTest {

  @InjectMock
  FantasySeriesService fantasySeriesService;

  @InjectMock
  FantasyDefinitionService fantasyDefinitionService;

  @InjectMock
  FantasyTeamMemberCategoryService fantasyTeamMemberCategoryService;

  final String series = FantasySeriesType.FORMULA_1.id;
  final int season = 2019;
  final int nextRace = 3;


  @Test
  public void shouldGetAllFantasySeries() {
    //given
    final List<FantasySeries> fantasySeries = List.of(
            FantasySeries.builder().id("f1").name("Formula 1").build(),
            FantasySeries.builder().id("f2").name("Formula 2").build(),
            FantasySeries.builder().id("f3").name("Formula 3").build());

    when(fantasySeriesService.getFantasySeries()).thenReturn(fantasySeries);

    FantasySeriesDefinition expected =
            FantasySeriesDefinition
                    .builder()
                    .fantasySeriesList(fantasySeries)
                    .selectedFantasySeriesId(FantasySeriesType.FORMULA_1.id)
                    .build();


    //when
    final FantasySeriesDefinition result = given()
            .when()
            .get("/api/v1/fantasy/defs/series")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasySeriesDefinition.class);

    //then
    assertNotNull(result);
    assertEquals(expected, result);

    verify(fantasySeriesService).getFantasySeries();
  }

  @Test
  void shouldGetFantasySeasons() {
    //given
    final List<Integer> seasons = List.of(2019, 2020);

    when(fantasyDefinitionService.getFantasySeasons(series)).thenReturn(seasons);
    when(fantasyDefinitionService.getCurrentSeason(series)).thenReturn(season);

    final FantasySeasonDefinition expected =
            FantasySeasonDefinition
                    .builder()
                    .series(series)
                    .fantasySeasons(toFantasySeasonViewItem(series, seasons))
                    .selectedFantasySeasonId(createFantasySeasonViewItemId(series, season))
                    .build();

    //when
    FantasySeasonDefinition result = given()
            .when()
            .get("/api/v1/fantasy/defs/series/{series}/seasons",
                    Map.of("series", series))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasySeasonDefinition.class);

    //then
    assertEquals(expected, result);

    verify(fantasyDefinitionService).getFantasySeasons(series);
    verify(fantasyDefinitionService).getFantasySeasons(anyString());
    verify(fantasyDefinitionService).getCurrentSeason(series);
    verify(fantasyDefinitionService).getCurrentSeason(anyString());
  }

  @Test
  void shouldGetFantasyRaces() {

    //given
    final List<OriginalRace> originalRaces = List.of(
            OriginalRace.builder().series(series).season(season).round(1).raceName("Race 1").build(),
            OriginalRace.builder().series(series).season(season).round(2).raceName("Race 2").build(),
            OriginalRace.builder().series(series).season(season).round(3).raceName("Race 3").build()
    );

    final FantasyRaceDefinition fantasyRaceDefinition = FantasyRaceDefinition
            .builder()
            .series(series)
            .season(season)
            .fantasyRaces(toFantasyRaceViewItem(originalRaces))
            .selectedFantasyRaceId(createFantasyRaceViewItemId(series, season, nextRace))
            .build();

    when(fantasyDefinitionService.getFantasyRaceDefinition(series, season)).thenReturn(fantasyRaceDefinition);

    //when
    FantasyRaceDefinition result = given()
            .when()
            .get("/api/v1/fantasy/defs/series/{series}/seasons/{season}/races",
                    Map.of("series", series, "season", season))
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(FantasyRaceDefinition.class);

    assertEquals(fantasyRaceDefinition, result);

    verify(fantasyDefinitionService).getFantasyRaceDefinition(series, season);
    verify(fantasyDefinitionService).getFantasyRaceDefinition(anyString(), anyInt());
  }

  @Test
  void shouldGetTeamMemberCategories() {
    List<FantasyTeamMemberCategory> teamMemberCategories = List.of(
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.DRIVER_1).isConstructor(false).name("Driver-1").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.DRIVER_2).isConstructor(false).name("Driver-2").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.BODY).isConstructor(true).name("Body").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.ENGINE).isConstructor(true).name("Engine").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.STAFF).isConstructor(true).name("Staff").build()
    );
    when(fantasyTeamMemberCategoryService.getFantasyTeamMemberCategories()).thenReturn(teamMemberCategories);

    //when
    List<FantasyTeamMemberCategory> result = given()
            .when()
            .get("/api/v1/fantasy/defs/fantasy/team/member/categories")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", FantasyTeamMemberCategory.class);

    //then
    assertNotNull(result);
    assertEquals(teamMemberCategories, result);

  }
}
