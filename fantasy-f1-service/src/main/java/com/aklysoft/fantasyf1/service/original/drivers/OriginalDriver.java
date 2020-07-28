package com.aklysoft.fantasyf1.service.original.drivers;

import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMember;
import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMemberPriceItem;
import com.aklysoft.fantasyf1.service.original.constructors.OriginalConstructor;
import com.aklysoft.fantasyf1.service.original.results.OriginalRaceResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OriginalDriver implements FantasyTeamMemberPriceItem {

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

  private String constructorId;

  private Long price;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false),
          @JoinColumn(name = "constructorId", referencedColumnName = "id", insertable = false, updatable = false)
  })
  private OriginalConstructor constructor;

  @JsonIgnore
  @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
  private List<OriginalRaceResult> raceResults;

  @JsonIgnore
  @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
  private List<FantasyTeamMember> fantasyTeamMembers;

  @Transient
  @Override
  public String getDisplayName() {
    return OriginalDriverMappers.getDriverDisplayName(this);
  }
}
