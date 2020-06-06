export interface FantasyTeamMemberItem {
  id: string;
  teamId: string;
  username: string;
  series: string;
  season: number;
  round: number;
  teamMemberTypeId: string; //TeamMemberCategoryType
  driverId: string; //viewId
  constructorId: string; //viewId
}

export interface FantasyTeamMemberItemExt extends FantasyTeamMemberItem {
  teamMemberName: string;
  teamMemberId: string;
  teamMemberCategoryName: string;
  sortOrder: number;
}

export interface SetTeamMember {

  id: string;
  teamMemberTypeId: string; //TeamMemberCategoryType
  race: number;
}
