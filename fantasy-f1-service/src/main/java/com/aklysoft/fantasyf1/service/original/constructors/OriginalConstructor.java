package com.aklysoft.fantasyf1.service.original.constructors;

import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResult;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

  @JsonBackReference
  @OneToMany(mappedBy = "constructor", cascade = CascadeType.ALL)
  private List<OriginalRaceResult> raceResults;
}
