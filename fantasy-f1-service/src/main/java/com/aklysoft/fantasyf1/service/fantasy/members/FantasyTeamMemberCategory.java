package com.aklysoft.fantasyf1.service.fantasy.members;

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
@Table(name = "fantasy_team_member_categories")
public class FantasyTeamMemberCategory {

  @Id
  @Enumerated(EnumType.STRING)
  private FantasyTeamMemberCategoryType id;

  private String name;

  private boolean isConstructor;

  private int sortOrder;

  @JsonIgnore
  @OneToMany(mappedBy = "teamMemberType", cascade = CascadeType.ALL)
  private List<FantasyTeamMember> fantasyTeamMembers;
}
