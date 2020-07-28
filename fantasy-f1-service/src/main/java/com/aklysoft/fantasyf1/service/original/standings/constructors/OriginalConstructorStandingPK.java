package com.aklysoft.fantasyf1.service.original.standings.constructors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalConstructorStandingPK implements Serializable {
  private String series;
  private int season;
  private String constructorId;
}
