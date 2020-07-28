package com.aklysoft.fantasyf1.service.fantasy.members;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Tag("integration")
class FantasyTeamMemberCategoryRepositoryImplTest {

  @Inject
  FantasyTeamMemberCategoryRepositoryImpl fantasyTeamMemberCategoryRepository;

  @Test
  public void test() {
    assertNotNull(fantasyTeamMemberCategoryRepository);
  }
}
