package com.aklysoft.fantasyf1.service.original.constructors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalConstructorVIewItem {

  private String id; //viewId

  private String series;
  private int season;
  private String constructorId;

  private String name;
  private String nationality;
  private String url;
  private Long price;

}
