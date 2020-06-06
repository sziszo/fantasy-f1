package com.aklysoft.fantasyf1.service.fantasy.definitions.series;

import com.aklysoft.fantasyf1.service.fantasy.definitions.FantasyDefinition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FantasySeries {

  @Id
  private String id;

  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "fantasySeries", cascade = CascadeType.ALL)
  List<FantasyDefinition> fantasyDefinitions;
}
