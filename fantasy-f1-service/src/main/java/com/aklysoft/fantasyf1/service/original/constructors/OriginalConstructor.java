package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMember;
import com.aklysoft.fantasyf1.service.original.drivers.OriginalDriver;
import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OriginalConstructorPK.class)
public class OriginalConstructor {

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private String id;

  private String name;

  private String nationality;

  private String url;

  @JsonIgnore
  @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL)
  private List<OriginalDriver> driver;

  @JsonIgnore
  @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL)
  private List<OriginalRaceResult> raceResults;

  @JsonIgnore
  @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL)
  private List<FantasyTeamMember> fantasyTeamMembers;

}
