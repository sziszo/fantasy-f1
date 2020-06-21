export interface TeamMemberCategoryItem {
  id: string; //TeamMemberCategoryType
  name: string;
  constructor: boolean;
  sortOrder: number;
}

export enum TeamMemberCategoryType {
  ENGINE, BODY, STAFF, DRIVER_1, DRIVER_2
}

export const getTeamMemberImageClass = (teamMemberCategoryType: string)  => {
  switch (TeamMemberCategoryType[teamMemberCategoryType]) {
    case TeamMemberCategoryType.DRIVER_1:
    case TeamMemberCategoryType.DRIVER_2:
      return "driver-image";
    case TeamMemberCategoryType.BODY:
      return "body-image";
    case TeamMemberCategoryType.STAFF:
      return "staff-image";
    default:
      return "engine-image";
  }
}
