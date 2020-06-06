import { createSelector } from "@ngrx/store";
import { selectCommonsState } from "../../reducers";
import { State } from "../../../reducers";
import * as fromDriverItem from "./driver-item.reducer";
import * as fromSeriesItem from "../series-item/series-item.selector";
import * as fromSeasonItem from "../season-item/season-item.selector";
import { DriverTableItem } from "./driver-item.model";
import { selectConstructorItemEntities } from "../constructor-item/constructor-item.selector";

export const selectDriverItemEntitiesState = createSelector(
  selectCommonsState,
  (state: State) => state[fromDriverItem.driverItemsFeatureKey]
);

export const selectAllDriverItems = createSelector(
  selectDriverItemEntitiesState,
  fromDriverItem.selectAll
);

export const selectDriverItemEntities = createSelector(
  selectDriverItemEntitiesState,
  fromDriverItem.selectEntities
);

export const selectDriverTableItems = createSelector(
  selectAllDriverItems,
  fromSeriesItem.getSelectedSeriesId,
  fromSeasonItem.getSelectedSeason,
  selectConstructorItemEntities,
  (drivers, series, season, constructors) =>
    drivers.filter(driver => driver.series == series && driver.season == season)
      .map(driver => {
        return {
          ...driver,
          constructorName: constructors[driver.constructorId].name,
          enabled: false
        } as DriverTableItem
      })
);

