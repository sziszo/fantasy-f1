package com.aklysoft.fantasyf1.service;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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

}
