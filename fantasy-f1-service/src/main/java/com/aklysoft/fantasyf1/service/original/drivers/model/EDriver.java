package com.aklysoft.fantasyf1.service.original.drivers.model;

import lombok.Data;

@Data
public class EDriver {

  private String driverId;
  private int permanentNumber;
  private String code;

  private String url;

  private String givenName;
  private String familyName;

  private String dateOfBirth;

  private String nationality;
}
