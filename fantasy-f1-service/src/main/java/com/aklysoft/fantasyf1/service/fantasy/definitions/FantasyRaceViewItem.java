package com.aklysoft.fantasyf1.service.fantasy.definitions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FantasyRaceViewItem {

  private String id;
  private String series;
  private Integer season;
  private Integer round;
  private String name;
  private LocalDateTime date;

}
