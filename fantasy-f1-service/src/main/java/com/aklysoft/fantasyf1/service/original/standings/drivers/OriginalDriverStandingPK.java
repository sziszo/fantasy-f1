package com.aklysoft.fantasyf1.service.original.standings.drivers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalDriverStandingPK implements Serializable {
  private String series;
  private int season;
  private String driverId;
}
