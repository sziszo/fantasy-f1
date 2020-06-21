package com.aklysoft.fantasyf1.service.fantasy.definitions;

import com.aklysoft.fantasyf1.service.fantasy.definitions.series.FantasySeries;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FantasyDefinitionPK.class)
public class FantasyDefinition {

  @Id
  private String series;

  @Id
  private Integer season;

  private Integer nextRace;

  @ManyToOne
  @JoinColumn(name = "series", referencedColumnName = "id", insertable = false, updatable = false)
  private FantasySeries fantasySeries;

}
