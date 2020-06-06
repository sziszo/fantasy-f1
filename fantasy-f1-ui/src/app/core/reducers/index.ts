import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector,
  createSelector,
  MetaReducer
} from '@ngrx/store';
import { environment } from '../../../environments/environment';
import * as fromSeriesItem from "../entities/series-item/series-item.reducer";
import * as fromSeasonItem from "../entities/season-item/season-item.reducer";
import * as fromRaceItem from '../entities/race-item/race-item.reducer';
import * as fromDriverItem from '../entities/driver-item/driver-item.reducer';
import * as fromConstructorItem from '../entities/constructor-item/constructor-item.reducer';
import * as fromTeamMemberCategoryItem from "../entities/team-member-category-item/team-member-category-item.reducer";

export const commonsFeatureKey = 'commons';

export interface State {
  [fromSeriesItem.seriesItemsFeatureKey]: fromSeriesItem.State;
  [fromSeasonItem.seasonItemsFeatureKey]: fromSeasonItem.State;
  [fromRaceItem.raceItemsFeatureKey]: fromRaceItem.State;
  [fromDriverItem.driverItemsFeatureKey]: fromDriverItem.State;
  [fromConstructorItem.constructorItemsFeatureKey]: fromConstructorItem.State;
  [fromTeamMemberCategoryItem.teamMemberCategoryItemsFeatureKey]: fromTeamMemberCategoryItem.State;
}

export const reducers: ActionReducerMap<State> = {
  [fromSeriesItem.seriesItemsFeatureKey]: fromSeriesItem.reducer,
  [fromSeasonItem.seasonItemsFeatureKey]: fromSeasonItem.reducer,
  [fromRaceItem.raceItemsFeatureKey]: fromRaceItem.reducer,
  [fromDriverItem.driverItemsFeatureKey]: fromDriverItem.reducer,
  [fromConstructorItem.constructorItemsFeatureKey]: fromConstructorItem.reducer,
  [fromTeamMemberCategoryItem.teamMemberCategoryItemsFeatureKey]: fromTeamMemberCategoryItem.reducer,
};


export const metaReducers: MetaReducer<State>[] = !environment.production ? [] : [];

export const selectCommonsState = createFeatureSelector<State>(commonsFeatureKey);
