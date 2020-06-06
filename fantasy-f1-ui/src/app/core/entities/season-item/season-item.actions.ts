import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { SeasonItem } from './season-item.model';

export const loadSeasonItems = createAction(
  '[SeasonItem/API] Load SeasonItems',
  props<{ seasonItems: SeasonItem[] }>()
);

export const addSeasonItem = createAction(
  '[SeasonItem/API] Add SeasonItem',
  props<{ seasonItem: SeasonItem }>()
);

export const upsertSeasonItem = createAction(
  '[SeasonItem/API] Upsert SeasonItem',
  props<{ seasonItem: SeasonItem }>()
);

export const addSeasonItems = createAction(
  '[SeasonItem/API] Add SeasonItems',
  props<{ seasonItems: SeasonItem[] }>()
);

export const upsertSeasonItems = createAction(
  '[SeasonItem/API] Upsert SeasonItems',
  props<{ seasonItems: SeasonItem[] }>()
);

export const updateSeasonItem = createAction(
  '[SeasonItem/API] Update SeasonItem',
  props<{ seasonItem: Update<SeasonItem> }>()
);

export const updateSeasonItems = createAction(
  '[SeasonItem/API] Update SeasonItems',
  props<{ seasonItems: Update<SeasonItem>[] }>()
);

export const deleteSeasonItem = createAction(
  '[SeasonItem/API] Delete SeasonItem',
  props<{ id: string }>()
);

export const deleteSeasonItems = createAction(
  '[SeasonItem/API] Delete SeasonItems',
  props<{ ids: string[] }>()
);

export const clearSeasonItems = createAction(
  '[SeasonItem/API] Clear SeasonItems'
);

export const setSelectedSeasonItem = createAction(
  '[SeriesItem/API] Set Selected SeasonItem',
  props<{ selectedSeasonItemId: number }>()
);
