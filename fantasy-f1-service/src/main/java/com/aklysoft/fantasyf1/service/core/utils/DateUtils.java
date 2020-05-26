package com.aklysoft.fantasyf1.service.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtils {

  /**
   * @param dateTimeString e.g. 2016-08-16T15:23:01Z
   * @return
   */
  public static LocalDateTime toUtc(String dateTimeString) {

    final ZoneId utc = ZoneId.of(ZoneOffset.UTC.getId());
    return LocalDateTime.ofInstant(Instant.parse(dateTimeString), utc);
  }
}
