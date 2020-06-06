import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { SeasonItem } from './season-item.model';

export const loadSeasonItems = createAction(
  '[SeasonItem] Load SeasonItems',
  props<{ seasonItems: SeasonItem[] }>()
);

export const addSeasonItem = createAction(
  '[SeasonItem] Add SeasonItem',
  props<{ seasonItem: SeasonItem }>()
);

export const upsertSeasonItem = createAction(
  '[SeasonItem] Upsert SeasonItem',
  props<{ seasonItem: SeasonItem }>()
);

export const addSeasonItems = createAction(
  '[SeasonItem] Add SeasonItems',
  props<{ seasonItems: SeasonItem[] }>()
);

export const upsertSeasonItems = createAction(
  '[SeasonItem] Upsert SeasonItems',
  props<{ seasonItems: SeasonItem[] }>()
);

export const updateSeasonItem = createAction(
  '[SeasonItem] Update SeasonItem',
  props<{ seasonItem: Update<SeasonItem> }>()
);

export const updateSeasonItems = createAction(
  '[SeasonItem] Update SeasonItems',
  props<{ seasonItems: Update<SeasonItem>[] }>()
);

export const deleteSeasonItem = createAction(
  '[SeasonItem] Delete SeasonItem',
  props<{ id: string }>()
);

export const deleteSeasonItems = createAction(
  '[SeasonItem] Delete SeasonItems',
  props<{ ids: string[] }>()
);

export const clearSeasonItems = createAction(
  '[SeasonItem] Clear SeasonItems'
);

export const setSelectedSeasonItem = createAction(
  '[SeasonItem] Set Selected SeasonItem',
  props<{ selectedSeasonItemId: string }>()
);

export const searchSeasonItems = createAction(
  '[SeasonItem/API] Search SeasonItems',
  props<{series: string}>()
);

export const searchSeasonItemsSuccess = createAction(
  '[SeasonItem/API] Search SeasonItems Success',
  props<{ seasonItems: SeasonItem[], selectedSeasonItemId: string }>()
);

export const searchSeasonItemsFailure = createAction(
  '[SeasonItem/API] Search SeasonItems Failure',
  props<{ error: any }>()
);
