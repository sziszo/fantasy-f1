import { State } from "../../../reducers";
import * as fromSeasonItem from "./season-item.reducer";
import * as fromSeriesItem from "../series-item/series-item.selector";
import { selectCommonsState } from "../../reducers";
import { createSelector } from "@ngrx/store";


export const selectSeasonItemEntitiesState = createSelector(
  selectCommonsState,
  (state: State) => state[fromSeasonItem.seasonItemsFeatureKey]
);

export const selectAllSeasonItems = createSelector(
  selectSeasonItemEntitiesState,
  fromSeasonItem.selectAll
);

export const selectAllSeasonItemEntities = createSelector(
  selectSeasonItemEntitiesState,
  fromSeasonItem.selectEntities
);

export const getSelectedSeasonId = createSelector(
  selectSeasonItemEntitiesState,
  fromSeasonItem.selectedSeasonId,
);

export const getSelectedSeasonItem = createSelector(
  selectAllSeasonItemEntities,
  getSelectedSeasonId,
  (ent, id) => !id ? null : ent[id]
)

export const getAllSeasonItems = createSelector(
  selectAllSeasonItems,
  fromSeriesItem.getSelectedSeriesId,
  (items, series) => items.filter(value => value.series == series)
);

export const getSelectedSeason = createSelector(
  getSelectedSeasonItem,
  (item) => item ? item.season : null
)
