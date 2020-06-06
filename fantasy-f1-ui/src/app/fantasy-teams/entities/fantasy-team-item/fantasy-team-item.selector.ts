import { createSelector } from "@ngrx/store";
import * as fromFantasyTeamItems from "./fantasy-team-item.reducer";
import { selectFantasyTeamsState } from "../../reducers";

export const selectFantasyTeamItemEntitiesState = createSelector(
  selectFantasyTeamsState,
  (state) => state[fromFantasyTeamItems.fantasyTeamItemsFeatureKey]
);

export const selectAllFantasyTeamItems = createSelector(
  selectFantasyTeamItemEntitiesState,
  fromFantasyTeamItems.selectAll
);

export const selectFantasyTeamItemEntities = createSelector(
  selectFantasyTeamItemEntitiesState,
  fromFantasyTeamItems.selectEntities
);

export const selectMyFantasyTeamItemId = createSelector(
  selectFantasyTeamItemEntitiesState,
  fromFantasyTeamItems.selectMyTeamId
);

export const selectMyFantasyTeamItem = createSelector(
  selectFantasyTeamItemEntities,
  selectMyFantasyTeamItemId,
  (ent, myTeamId) => myTeamId ? ent[myTeamId] : null
);
