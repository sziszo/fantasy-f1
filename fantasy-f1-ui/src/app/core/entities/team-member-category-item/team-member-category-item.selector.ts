import { createSelector } from "@ngrx/store";
import * as fromTeamMemberCategoryItems from "./team-member-category-item.reducer";
import { selectCommonsState } from "../../reducers";

export const selectTeamMemberCategoryItemEntitiesState = createSelector(
  selectCommonsState,
  (state) => state[fromTeamMemberCategoryItems.teamMemberCategoryItemsFeatureKey]
);

export const selectTeamMemberItemEntities = createSelector(
  selectTeamMemberCategoryItemEntitiesState,
  fromTeamMemberCategoryItems.selectEntities
);

export const selectAllTeamMemberItems = createSelector(
  selectTeamMemberCategoryItemEntitiesState,
  fromTeamMemberCategoryItems.selectAll
);

export const selectAllSortedTeamMemberItems = createSelector(
  selectAllTeamMemberItems,
  (items) => items.sort((a, b) => a.sortOrder - b.sortOrder)
)

