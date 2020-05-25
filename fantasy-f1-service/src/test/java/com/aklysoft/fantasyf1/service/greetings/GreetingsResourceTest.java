package com.aklysoft.fantasyf1.service.greetings;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingsResourceTest {

  @Test
  public void testHelloEndpoint() {
    given()
            .when().get("/api/v1/hello")
            .then()
            .statusCode(200)
            .body(containsString("hello"));
  }

}
