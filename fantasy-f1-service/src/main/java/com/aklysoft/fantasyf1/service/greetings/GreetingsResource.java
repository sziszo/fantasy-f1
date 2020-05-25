package com.aklysoft.fantasyf1.service.greetings;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/hello")
@Tag(name = "admin")
public class GreetingsResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return "hello";
  }
}
