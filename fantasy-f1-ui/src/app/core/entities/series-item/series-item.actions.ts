import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { SeriesItem } from './series-item.model';

export const loadSeriesItems = createAction(
  '[SeriesItem/API] Load SeriesItems',
  props<{ seriesItems: SeriesItem[] }>()
);

export const addSeriesItem = createAction(
  '[SeriesItem/API] Add SeriesItem',
  props<{ seriesItem: SeriesItem }>()
);

export const upsertSeriesItem = createAction(
  '[SeriesItem/API] Upsert SeriesItem',
  props<{ seriesItem: SeriesItem }>()
);

export const addSeriesItems = createAction(
  '[SeriesItem/API] Add SeriesItems',
  props<{ seriesItems: SeriesItem[] }>()
);

export const upsertSeriesItems = createAction(
  '[SeriesItem/API] Upsert SeriesItems',
  props<{ seriesItems: SeriesItem[] }>()
);

export const updateSeriesItem = createAction(
  '[SeriesItem/API] Update SeriesItem',
  props<{ seriesItem: Update<SeriesItem> }>()
);

export const updateSeriesItems = createAction(
  '[SeriesItem/API] Update SeriesItems',
  props<{ seriesItems: Update<SeriesItem>[] }>()
);

export const deleteSeriesItem = createAction(
  '[SeriesItem/API] Delete SeriesItem',
  props<{ id: string }>()
);

export const deleteSeriesItems = createAction(
  '[SeriesItem/API] Delete SeriesItems',
  props<{ ids: string[] }>()
);

export const clearSeriesItems = createAction(
  '[SeriesItem/API] Clear SeriesItems'
);

export const setSelectedSeriesItem = createAction(
  '[SeriesItem/API] Set Selected SeriesItem',
  props<{ selectedSeriesItemId: string }>()
);
