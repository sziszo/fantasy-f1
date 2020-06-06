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

export const commonsFeatureKey = 'commons';

export interface State {
  [fromSeriesItem.seriesItemsFeatureKey]: fromSeriesItem.State;
  [fromSeasonItem.seasonItemsFeatureKey]: fromSeasonItem.State;
}

export const reducers: ActionReducerMap<State> = {
  [fromSeriesItem.seriesItemsFeatureKey]: fromSeriesItem.reducer,
  [fromSeasonItem.seasonItemsFeatureKey]: fromSeasonItem.reducer,
};


export const metaReducers: MetaReducer<State>[] = !environment.production ? [] : [];

export const selectCommonsState = createFeatureSelector<State>(commonsFeatureKey);
