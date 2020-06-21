import { createSelector } from "@ngrx/store";
import { selectFantasyTeamsState } from "../../reducers";
import * as fromFantasyTeamMemberItems from "./fantasy-team-member-item.reducer";
import { selectMyFantasyTeamItemId } from "../fantasy-team-item/fantasy-team-item.selector";
import { selectTeamMemberItemEntities } from "../../../core/entities/team-member-category-item/team-member-category-item.selector";
import { FantasyTeamMemberItemExt } from "./fantasy-team-member-item.model";
import { selectDriverItemEntities } from "../../../core/entities/driver-item/driver-item.selector";
import { selectConstructorItemEntities } from "../../../core/entities/constructor-item/constructor-item.selector";
import { TeamMemberCategoryType } from "../../../core/entities/team-member-category-item/team-member-category-item.model";
import * as fromSeriesItem from "../../../core/entities/series-item/series-item.selector";
import * as fromSeasonItem from "../../../core/entities/season-item/season-item.selector";

export const selectFantasyTeamMemberItemEntitiesState = createSelector(
  selectFantasyTeamsState,
  (state) => state[fromFantasyTeamMemberItems.fantasyTeamMemberItemsFeatureKey]
);

export const selectAllFantasyTeamItems = createSelector(
  selectFantasyTeamMemberItemEntitiesState,
  fromFantasyTeamMemberItems.selectAll
);

export const selectCurrentFantasyTeamItems = createSelector(
  selectAllFantasyTeamItems,
  fromSeriesItem.getSelectedSeriesId,
  fromSeasonItem.getSelectedSeason,
  (members, series, season) =>
    members.filter(member => member.series == series && member.season == season)
);

export const selectMyFantasyTeamItems = createSelector(
  selectCurrentFantasyTeamItems,
  selectMyFantasyTeamItemId,
  selectTeamMemberItemEntities,
  selectDriverItemEntities,
  selectConstructorItemEntities,
  (members, myTeamId, categories, drivers, constructors) =>
    members.filter(member => member.teamId == myTeamId)
      .map(member => {
        // console.log('selectMyFantasyTeamItems executed')
        let teamMemberCategoryType = TeamMemberCategoryType[member.teamMemberTypeId];
        let isConstructor = teamMemberCategoryType != TeamMemberCategoryType.DRIVER_1 && teamMemberCategoryType != TeamMemberCategoryType.DRIVER_2;
        let category = categories[member.teamMemberTypeId];
        return {
          ...member,
          teamMemberCategoryName: category.name,
          sortOrder: category.sortOrder,
          teamMemberId: isConstructor ? member.constructorId : member.driverId,
          teamMemberName: isConstructor ?
            (member.constructorId ? constructors[member.constructorId].name : null) :
            (member.driverId ? drivers[member.driverId].name : null)
        } as FantasyTeamMemberItemExt
      })
      .sort((a, b) => a.sortOrder - b.sortOrder)
);

export const selectAllTeamMemberConstructor = createSelector(
  selectCurrentFantasyTeamItems,
  selectDriverItemEntities,

  (members, drivers) =>
    members.map(member => {
      let teamMemberCategoryType = TeamMemberCategoryType[member.teamMemberTypeId];
      let isConstructor = teamMemberCategoryType != TeamMemberCategoryType.DRIVER_1 && teamMemberCategoryType != TeamMemberCategoryType.DRIVER_2;

      return isConstructor ? member.constructorId : drivers[member.driverId]?.constructorId;
    })
)
