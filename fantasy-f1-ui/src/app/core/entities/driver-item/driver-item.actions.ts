import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { DriverItem } from './driver-item.model';

export const loadDriverItems = createAction(
  '[DriverItem] Load DriverItems',
  props<{ driverItems: DriverItem[] }>()
);

export const addDriverItem = createAction(
  '[DriverItem] Add DriverItem',
  props<{ driverItem: DriverItem }>()
);

export const upsertDriverItem = createAction(
  '[DriverItem] Upsert DriverItem',
  props<{ driverItem: DriverItem }>()
);

export const addDriverItems = createAction(
  '[DriverItem] Add DriverItems',
  props<{ driverItems: DriverItem[] }>()
);

export const upsertDriverItems = createAction(
  '[DriverItem] Upsert DriverItems',
  props<{ driverItems: DriverItem[] }>()
);

export const updateDriverItem = createAction(
  '[DriverItem] Update DriverItem',
  props<{ driverItem: Update<DriverItem> }>()
);

export const updateDriverItems = createAction(
  '[DriverItem] Update DriverItems',
  props<{ driverItems: Update<DriverItem>[] }>()
);

export const deleteDriverItem = createAction(
  '[DriverItem] Delete DriverItem',
  props<{ id: string }>()
);

export const deleteDriverItems = createAction(
  '[DriverItem] Delete DriverItems',
  props<{ ids: string[] }>()
);

export const clearDriverItems = createAction(
  '[DriverItem] Clear DriverItems'
);

export const searchDriverItems = createAction(
  '[DriverItem/API] Search DriverItems',
  props<{ series: string, season: number }>()
);

export const searchDriverItemsSuccess = createAction(
  '[DriverItem/API] Search DriverItems Success',
  props<{ data: DriverItem[] }>()
);

export const searchDriverItemsFailure = createAction(
  '[DriverItem/API] Search DriverItems Failure',
  props<{ error: any }>()
);
