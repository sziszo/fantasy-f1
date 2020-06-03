package com.aklysoft.fantasyf1.service.fantasy.members;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.container.ResourceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FantasyTeamMemberResourceFactoryTest {

  @InjectMocks
  FantasyTeamMemberResourceFactory fantasyTeamMemberResourceFactory;

  @Mock
  FantasyTeamMemberService fantasyTeamMemberService;

  @Test
  public void shouldCreate() {

    //given
    final ResourceContext resourceContext = mock(ResourceContext.class);
    when(resourceContext.getResource(FantasyTeamMemberResource.class)).thenReturn(new FantasyTeamMemberResource());

    //when
    final FantasyTeamMemberResource result = fantasyTeamMemberResourceFactory.create(resourceContext);

    //then
    assertNotNull(result);
    assertEquals(FantasyTeamMemberResource.class, result.getClass());

  }


}
