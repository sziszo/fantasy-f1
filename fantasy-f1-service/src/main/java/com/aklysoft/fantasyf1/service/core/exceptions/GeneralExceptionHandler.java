package com.aklysoft.fantasyf1.service.core.exceptions;

import com.aklysoft.fantasyf1.service.core.AppConfiguration;
import org.codehaus.plexus.util.ExceptionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class GeneralExceptionHandler implements ExceptionMapper<Exception> {

  @Inject
  AppConfiguration appConfiguration;

  @Override
  public Response toResponse(Exception exception) {
    if (exception instanceof WebApplicationException) {
      return ((WebApplicationException) exception).getResponse();
    }

    StringBuilder rsp = new StringBuilder("An error occurred!");
    if (exception.getMessage() != null) {
      rsp.append("message: ").append(exception.getMessage());
    }

    if ("true".equals(appConfiguration.showStacktrace)) {
      rsp.append("\n").append(ExceptionUtils.getStackTrace(exception));
    }

    return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(rsp.toString())
            .build();
  }

}
