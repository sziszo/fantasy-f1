import { State } from "../../../reducers";
import * as fromSeriesItem from "./series-item.reducer";
import { createSelector } from "@ngrx/store";
import { selectCommonsState } from "../../reducers";

export const selectSeriesItemEntitiesState = createSelector(
  selectCommonsState,
  (state: State) => state[fromSeriesItem.seriesItemsFeatureKey]
);

export const selectAllSeriesItems = createSelector(
  selectSeriesItemEntitiesState,
  fromSeriesItem.selectAll
);

export const selectSeriesItemEntities = createSelector(
  selectSeriesItemEntitiesState,
  fromSeriesItem.selectEntities
);

export const getSelectedSeriesId = createSelector(
  selectSeriesItemEntitiesState,
  fromSeriesItem.selectedSeriesId,
);

export const getSelectedSeriesItem = createSelector(
  selectSeriesItemEntities,
  getSelectedSeriesId,
  (ent, id) => !id ? null : ent[id]
);
