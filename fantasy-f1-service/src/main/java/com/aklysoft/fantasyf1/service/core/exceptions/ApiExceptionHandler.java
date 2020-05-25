package com.aklysoft.fantasyf1.service.core.exceptions;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import org.codehaus.plexus.util.ExceptionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ApiExceptionHandler implements ExceptionMapper<ApiException> {

  private final AppConfiguration appConfiguration;

  public ApiExceptionHandler(AppConfiguration appConfiguration) {
    this.appConfiguration = appConfiguration;
  }

  @Override
  public Response toResponse(ApiException apiException) {
    StringBuilder rsp = new StringBuilder(apiException.getMessage());
    if ("true".equals(appConfiguration.showStacktrace)) {
      rsp.append("\n").append(ExceptionUtils.getStackTrace(apiException));
    }
    return Response.status(Response.Status.BAD_REQUEST).entity(rsp.toString()).build();
  }

}
