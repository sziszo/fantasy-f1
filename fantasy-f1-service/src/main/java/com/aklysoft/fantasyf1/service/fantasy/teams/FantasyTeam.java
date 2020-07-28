package com.aklysoft.fantasyf1.service.fantasy.teams;

import com.aklysoft.fantasyf1.service.fantasy.members.FantasyTeamMember;
import com.aklysoft.fantasyf1.service.players.Player;
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
@IdClass(FantasyTeamPK.class)
public class FantasyTeam {

  @Id
  private String userName;

  @Id
  private String series;

  @Id
  private int season;

  @Column(unique = true)
  private String name;

  private Long money;

  private String creator;
  private LocalDateTime created;

  private String modifier;
  private LocalDateTime modified;

  @OneToOne
  @JoinColumn(name = "userName", insertable = false, updatable = false)
  Player player;

  @JsonIgnore
  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  List<FantasyTeamMember> teamMembers;
}
