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
public class FantasyTeamMemberType {

  @Id
  @Enumerated(EnumType.STRING)
  private FantasyTeamMemberTypeId id;

  private String name;

  private boolean isConstructor;

  @JsonIgnore
  @OneToMany(mappedBy = "teamMemberType", cascade = CascadeType.ALL)
  private List<FantasyTeamMember> fantasyTeamMembers;
}
