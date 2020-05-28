package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResult;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OriginalDriverPK.class)
public class OriginalDriver {

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private String id;

  private int permanentNumber;
  private String code;

  private String url;

  private String givenName;
  private String familyName;

  private LocalDate dateOfBirth;

  private String nationality;

  @JsonBackReference
  @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
  private List<OriginalRaceResult> raceResults;

}
