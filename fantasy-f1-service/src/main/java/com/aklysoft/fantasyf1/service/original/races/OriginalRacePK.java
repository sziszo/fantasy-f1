package com.aklysoft.fantasyf1.service.original.races;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalRacePK implements Serializable {

  private String series;
  private int season;
  private int round;

}
