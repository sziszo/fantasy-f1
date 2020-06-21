package com.aklysoft.fantasyf1.service;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinitionService;
import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeamService;
import com.aklysoft.fantasyf1.service.fantasy.teams.NewFantasyTeam;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructorService;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriverService;
import com.aklysoft.fantasyf1.service.original.races.OriginalRaceService;
import com.aklysoft.fantasyf1.service.users.UserService;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.configuration.ProfileManager;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.ws.rs.core.Application;

@QuarkusMain
public class FantasyF1ServiceMain {

  public static void main(String... args) {
    System.out.println("Running main method");
    Quarkus.run(args);
  }

  @OpenAPIDefinition(
          tags = {
                  @Tag(name = "player", description = "Player operations."),
                  @Tag(name = "admin", description = "Admin Operations.")
          },
          info = @Info(
                  title = "Fantasy-F1 API",
                  version = "1.0.0",
                  contact = @Contact(
                          name = "Fantasy-F1 Support",
                          url = "http://fantasyf1.com/contact",
                          email = "szilard.antal@aklysoft.hu"),
                  license = @License(
                          name = "Apache 2.0",
                          url = "http://www.apache.org/licenses/LICENSE-2.0.html"))
  )
  public static class FantasyF1RsApp extends Application {
  }

  @Singleton
  public static class Startup {
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    private final UserService userService;
    private final FantasyDefinitionService fantasyDefinitionService;
    private final OriginalRaceService originalRaceService;
    private final OriginalConstructorService originalConstructorService;
    private final OriginalDriverService originalDriverService;
    private final FantasyTeamService fantasyTeamService;


    public Startup(UserService userService, FantasyDefinitionService fantasyDefinitionService,
                   OriginalRaceService originalRaceService, OriginalConstructorService originalConstructorService,
                   OriginalDriverService originalDriverService, FantasyTeamService fantasyTeamService) {
      this.userService = userService;
      this.originalRaceService = originalRaceService;
      this.fantasyDefinitionService = fantasyDefinitionService;
      this.originalConstructorService = originalConstructorService;
      this.originalDriverService = originalDriverService;
      this.fantasyTeamService = fantasyTeamService;
    }

    @Transactional
    public void startup(@Observes StartupEvent evt) {
      LOGGER.info("The application is starting with profile " + ProfileManager.getActiveProfile());

      loadUsers();
//      if (!ProfileManager.getActiveProfile().equals("prod")) {
//        loadUsers();
//      }

      if (ProfileManager.getActiveProfile().equals("dev")) {
        final String series = "f1";
        final Integer season = fantasyDefinitionService.getCurrentSeason(series);

        loadCurrentSeason(series, season);
        createTestFantasyTeam(series, season);
      }
    }

    private void loadUsers() {
      // load all test users
      userService.addUser("admin", "admin", "admin");
      userService.addUser("user", "user", "user");
      userService.addUser("sziszo", "sziszo", "user");
      userService.addUser("john", "john", "user");
      userService.addUser("charlie", "charlie", "user");
    }

    private void loadCurrentSeason(String series, int season) {
      originalRaceService.getRaces(series, season);
      originalConstructorService.getConstructors(series, season);
      originalDriverService.getDrivers(series, season);
      originalDriverService.linkConstructorsAndDrivers(series, season);
    }

    private void createTestFantasyTeam(String series, int season) {
      fantasyTeamService.createFantasyTeam(
              NewFantasyTeam
                      .builder()
                      .series(series)
                      .season(season)
                      .username("user")
                      .creator("admin")
                      .name("TestF1Team")
                      .build()
      );
    }

  }
}
