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
class ApiExceptionHandlerTest {

  @InjectMocks
  ApiExceptionHandler apiExceptionHandler;

  @Mock
  AppConfiguration appConfiguration;

  @Test
  public void shouldReturnResponseWithoutStackTrace() {
    appConfiguration.showStacktrace = "false";
    Response rsp = apiExceptionHandler.toResponse(new ApiException("some error"));
    assertFalse(((String)rsp.getEntity()).contains(ApiExceptionHandler.class.getName()));
  }

  @Test
  public void shouldReturnResponseWithStackTrace() {
    appConfiguration.showStacktrace = "true";
    Response rsp = apiExceptionHandler.toResponse(new ApiException("some error"));
    assertTrue(((String)rsp.getEntity()).contains(ApiExceptionHandler.class.getName()));
  }
}
