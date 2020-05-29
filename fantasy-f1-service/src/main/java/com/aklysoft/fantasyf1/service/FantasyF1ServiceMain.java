package com.aklysoft.fantasyf1.service;

import com.aklysoft.fantasyf1.service.users.UserService;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
    private final UserService userService;

    public Startup(UserService userService) {
      this.userService = userService;
    }

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
      // load all test users
      userService.addUser("admin", "admin", "admin");
      userService.addUser("user", "user", "user");
      userService.addUser("sziszo", "sziszo", "user");
      userService.addUser("john", "john", "user");
      userService.addUser("charlie", "charlie", "user");
    }
  }
}
