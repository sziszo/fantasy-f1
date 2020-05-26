package com.aklysoft.fantasyf1.service.original.results;

import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.races.OriginalRace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OriginalRaceResultPK.class)
public class OriginalRaceResult {

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private int round;

  @Id
  private int position;

  private int number;
  private String positionText;
  private int points;

  private int grid;
  private int laps;
  private String status;

  @ManyToOne
  private OriginalRace race;

  @ManyToOne
  private OriginalDriver driver;

  @ManyToOne
  private OriginalConstructor constructor;
}
