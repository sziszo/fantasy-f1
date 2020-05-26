package com.aklysoft.fantasyf1.service.original.constructors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalConstructorPK implements Serializable {

  private String series;
  private int season;
  private String id;

}
