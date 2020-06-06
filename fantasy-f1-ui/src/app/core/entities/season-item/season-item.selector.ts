import { State } from "../../../reducers";
import * as fromSeasonItem from "./season-item.reducer";
import { createSelector } from "@ngrx/store";
import { selectCommonsState } from "../../reducers";


export const selectSeasonItemEntitiesState = createSelector(
  selectCommonsState,
  (state: State) => state[fromSeasonItem.seasonItemsFeatureKey]
);

export const selectAllSeasonItems = createSelector(
  selectSeasonItemEntitiesState,
  fromSeasonItem.selectAll
);

export const getSelectedSeasonId = createSelector(
  selectSeasonItemEntitiesState,
  fromSeasonItem.selectedSeasonId,
);
