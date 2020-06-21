package com.aklysoft.fantasyf1.service.fantasy.members;

import com.aklysoft.fantasyf1.service.fantasy.teams.FantasyTeam;
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
@IdClass(FantasyTeamMemberPK.class)
public class FantasyTeamMember {

  @Id
  private String userName;

  @Id
  private String series;

  @Id
  private int season;

  @Id
  private int round;

  @Id
  @Enumerated(EnumType.STRING)
  private FantasyTeamMemberCategoryType teamMemberTypeId;

  private String driverId;

  private String constructorId;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "userName", referencedColumnName = "userName", insertable = false, updatable = false),
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false)
  })
  private FantasyTeam team;

  @OneToOne
  @JoinColumns({
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false),
          @JoinColumn(name = "round", referencedColumnName = "round", insertable = false, updatable = false),
  })
  private OriginalRace race;

  @ManyToOne
  @JoinColumn(name = "teamMemberTypeId", insertable = false, updatable = false)
  private FantasyTeamMemberCategory teamMemberType;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false),
          @JoinColumn(name = "driverId", referencedColumnName = "id", insertable = false, updatable = false)
  })
  private OriginalDriver driver;

  @ManyToOne
  @JoinColumns({
          @JoinColumn(name = "series", referencedColumnName = "series", insertable = false, updatable = false),
          @JoinColumn(name = "season", referencedColumnName = "season", insertable = false, updatable = false),
          @JoinColumn(name = "constructorId", referencedColumnName = "id", insertable = false, updatable = false)
  })
  private OriginalConstructor constructor;

}
