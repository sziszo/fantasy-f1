import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { RaceItem } from './race-item.model';

export const loadRaceItems = createAction(
  '[RaceItem] Load RaceItems',
  props<{ raceItems: RaceItem[] }>()
);

export const addRaceItem = createAction(
  '[RaceItem] Add RaceItem',
  props<{ raceItem: RaceItem }>()
);

export const upsertRaceItem = createAction(
  '[RaceItem] Upsert RaceItem',
  props<{ raceItem: RaceItem }>()
);

export const addRaceItems = createAction(
  '[RaceItem] Add RaceItems',
  props<{ raceItems: RaceItem[] }>()
);

export const upsertRaceItems = createAction(
  '[RaceItem] Upsert RaceItems',
  props<{ raceItems: RaceItem[] }>()
);

export const updateRaceItem = createAction(
  '[RaceItem] Update RaceItem',
  props<{ raceItem: Update<RaceItem> }>()
);

export const updateRaceItems = createAction(
  '[RaceItem] Update RaceItems',
  props<{ raceItems: Update<RaceItem>[] }>()
);

export const deleteRaceItem = createAction(
  '[RaceItem] Delete RaceItem',
  props<{ id: string }>()
);

export const deleteRaceItems = createAction(
  '[RaceItem] Delete RaceItems',
  props<{ ids: string[] }>()
);

export const clearRaceItems = createAction(
  '[RaceItem] Clear RaceItems'
);

export const setSelectedRaceItem = createAction(
  '[RaceItem] Set Selected RaceItem',
  props<{ selectedRaceItemId: string }>()
);


export const searchRaceItems = createAction(
  '[RaceItem/API] Search RaceItems',
  props<{ series: string, season: number }>()
);

export const searchRaceItemsSuccess = createAction(
  '[RaceItem/API] Search RaceItems Success',
  props<{ raceItems: RaceItem[], selectedRaceItemId: string }>()
);

export const searchRaceItemsFailure = createAction(
  '[RaceItem/API] Search RaceItems Failure',
  props<{ error: any }>()
);
