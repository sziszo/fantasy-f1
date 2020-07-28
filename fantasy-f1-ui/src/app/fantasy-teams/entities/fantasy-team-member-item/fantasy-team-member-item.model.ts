import { FantasyTeamItem } from "../fantasy-team-item/fantasy-team-item.model";

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
  price: number;
}

export interface SetTeamMember {

  id: string;
  teamMemberTypeId: string; //TeamMemberCategoryType
  race: number;
}

export interface SetTeamMemberResponse {
  fantasyTeamMemberItem: FantasyTeamMemberItem;
  fantasyTeamItem: FantasyTeamItem
}

export interface DeleteTeamMemberResponse {
  fantasyTeamMemberItem: FantasyTeamMemberItem;
  fantasyTeamItem: FantasyTeamItem
}
