package com.aklysoft.fantasyf1.service.core.exceptions;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GeneralExceptionHandlerTest {

  @InjectMocks
  GeneralExceptionHandler generalExceptionHandler;

  @Mock
  AppConfiguration appConfiguration;


  @Test
  public void shouldReturnResponseWithoutStackTrace() {
    appConfiguration.showStacktrace = "false";
    Response rsp = generalExceptionHandler.toResponse(new Exception());

    assertFalse(((String)rsp.getEntity()).contains(GeneralExceptionHandler.class.getName()));
  }

  @Test
  public void shouldReturnResponseWithStackTrace() {
    appConfiguration.showStacktrace = "true";
    Response rsp = generalExceptionHandler.toResponse(new Exception("some error"));

    assertTrue(((String)rsp.getEntity()).contains("message: some error"));
    assertTrue(((String)rsp.getEntity()).contains(GeneralExceptionHandler.class.getName()));
  }
}
