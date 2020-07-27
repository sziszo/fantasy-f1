package com.aklysoft.fantasyf1.service.original.standings.constructors;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Tag("integration")
class OriginalConstructorStandingRepositoryImplTest {

  @Inject
  OriginalConstructorStandingRepositoryImpl originalConstructorStandingRepository;

  @Test
  public void test() {
    assertNotNull(originalConstructorStandingRepository);
  }
}
