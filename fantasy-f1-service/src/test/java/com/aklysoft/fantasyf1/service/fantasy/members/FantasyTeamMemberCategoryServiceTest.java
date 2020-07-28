package com.aklysoft.fantasyf1.service.fantasy.members;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FantasyTeamMemberCategoryServiceTest {

  @InjectMocks
  FantasyTeamMemberCategoryService fantasyTeamMemberCategoryService;

  @Mock
  FantasyTeamMemberCategoryRepository fantasyTeamMemberCategoryRepository;

  @Test
  public void testGetFantasyTeamMemberCategories() {
    List<FantasyTeamMemberCategory> teamMemberCategories = List.of(
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.DRIVER_1).isConstructor(false).name("Driver-1").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.DRIVER_2).isConstructor(false).name("Driver-2").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.BODY).isConstructor(true).name("Body").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.ENGINE).isConstructor(true).name("Engine").build(),
            FantasyTeamMemberCategory.builder().id(FantasyTeamMemberCategoryType.STAFF).isConstructor(true).name("Staff").build()
    );
    when(fantasyTeamMemberCategoryRepository.findAll()).thenReturn(teamMemberCategories);

    assertEquals(teamMemberCategories, fantasyTeamMemberCategoryService.getFantasyTeamMemberCategories());
  }

}
