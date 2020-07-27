package com.aklysoft.fantasyf1.service.original.drivers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalDriverViewItem {

  private String id; //viewId

  private String series;
  private int season;
  private String driverId;

  private String name;
  private int permanentNumber;
  private String url;

  private Long price;

  private String constructorId; //viewId
}
