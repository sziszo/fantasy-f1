import { createSelector } from "@ngrx/store";
import { selectCommonsState } from "../../reducers";
import { State } from "../../../reducers";
import * as fromConstructorItem from "./constructor-item.reducer";
import * as fromSeriesItem from "../series-item/series-item.selector";
import * as fromSeasonItem from "../season-item/season-item.selector";
import { ConstructorTableItem } from "./constructor-item.model";


export const selectConstructorItemEntitiesState = createSelector(
  selectCommonsState,
  (state: State) => state[fromConstructorItem.constructorItemsFeatureKey]
);

export const selectAllConstructorItems = createSelector(
  selectConstructorItemEntitiesState,
  fromConstructorItem.selectAll
);

export const selectConstructorItemEntities = createSelector(
  selectConstructorItemEntitiesState,
  fromConstructorItem.selectEntities
);

export const selectConstructorTableItems = createSelector(
  selectAllConstructorItems,
  fromSeriesItem.getSelectedSeriesId,
  fromSeasonItem.getSelectedSeason,
  (constructors, series, season) =>
    constructors.filter(constructor => constructor.series == series && constructor.season == season)
      .map(constructor => {
        // console.log('selectConstructorTableItems executed')
        return {
          ...constructor,
          enabled: false
        } as ConstructorTableItem
      })
)

