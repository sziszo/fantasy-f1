import { createSelector } from "@ngrx/store";
import * as fromRaceItem from "./race-item.reducer";
import { selectCommonsState } from "../../reducers";
import { State } from "../../../reducers";
import * as fromSeriesItem from "../series-item/series-item.selector";
import * as fromSeasonItem from "../season-item/season-item.selector";

export const selectRaceItemEntitiesState = createSelector(
  selectCommonsState,
  (state: State) => state[fromRaceItem.raceItemsFeatureKey]
);

export const selectAllRaceItems = createSelector(
  selectRaceItemEntitiesState,
  fromRaceItem.selectAll
);

export const selectAllRaceItemEntities = createSelector(
  selectRaceItemEntitiesState,
  fromRaceItem.selectEntities
);

export const getSelectedRaceId = createSelector(
  selectRaceItemEntitiesState,
  fromRaceItem.selectedRaceId,
);

export const getSelectedRaceItem = createSelector(
  selectAllRaceItemEntities,
  getSelectedRaceId,
  (ent, id) => !id ? null : ent[id]
)

export const getAllRaceItems = createSelector(
  selectAllRaceItems,
  fromSeriesItem.getSelectedSeriesId,
  fromSeasonItem.getSelectedSeason,
  (items, series, season) => items.filter(value => value.series == series && value.season == season)
);
