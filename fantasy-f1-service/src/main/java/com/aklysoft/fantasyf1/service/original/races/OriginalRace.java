package com.aklysoft.fantasyf1.service.original.races;

import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OriginalRacePK.class)
public class OriginalRace {

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private int round;

  private String raceName;
  private LocalDateTime date;

  @JsonIgnore
  @OneToMany(mappedBy = "race", cascade = CascadeType.ALL)
  private List<OriginalRaceResult> raceResults;

}
