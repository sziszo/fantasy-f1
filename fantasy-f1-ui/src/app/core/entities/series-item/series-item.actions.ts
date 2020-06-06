import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { SeriesItem } from './series-item.model';

export const loadSeriesItems = createAction(
  '[SeriesItem] Load SeriesItems',
  props<{ seriesItems: SeriesItem[] }>()
);

export const addSeriesItem = createAction(
  '[SeriesItem] Add SeriesItem',
  props<{ seriesItem: SeriesItem }>()
);

export const upsertSeriesItem = createAction(
  '[SeriesItem] Upsert SeriesItem',
  props<{ seriesItem: SeriesItem }>()
);

export const addSeriesItems = createAction(
  '[SeriesItem] Add SeriesItems',
  props<{ seriesItems: SeriesItem[] }>()
);

export const upsertSeriesItems = createAction(
  '[SeriesItem] Upsert SeriesItems',
  props<{ seriesItems: SeriesItem[] }>()
);

export const updateSeriesItem = createAction(
  '[SeriesItem] Update SeriesItem',
  props<{ seriesItem: Update<SeriesItem> }>()
);

export const updateSeriesItems = createAction(
  '[SeriesItem] Update SeriesItems',
  props<{ seriesItems: Update<SeriesItem>[] }>()
);

export const deleteSeriesItem = createAction(
  '[SeriesItem] Delete SeriesItem',
  props<{ id: string }>()
);

export const deleteSeriesItems = createAction(
  '[SeriesItem] Delete SeriesItems',
  props<{ ids: string[] }>()
);

export const clearSeriesItems = createAction(
  '[SeriesItem] Clear SeriesItems'
);

export const setSelectedSeriesItem = createAction(
  '[SeriesItem] Set Selected SeriesItem',
  props<{ selectedSeriesItemId: string }>()
);

export const searchSeriesItems = createAction(
  '[SeriesItem/API] Search SeriesItems',
);

export const searchSeriesItemsSuccess = createAction(
  '[SeriesItem/API] Search SeriesItems Success',
  props<{seriesItems: SeriesItem[], selectedSeriesItemId: string}>()
);

export const searchSeriesItemsFailure = createAction(
  '[SeriesItem/API] Search SeriesItems Failure',
  props<{ error: any }>()
);
