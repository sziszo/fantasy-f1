import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { ConstructorItem } from './constructor-item.model';

export const loadConstructorItems = createAction(
  '[ConstructorItem] Load ConstructorItems',
  props<{ constructorItems: ConstructorItem[] }>()
);

export const addConstructorItem = createAction(
  '[ConstructorItem] Add ConstructorItem',
  props<{ constructorItem: ConstructorItem }>()
);

export const upsertConstructorItem = createAction(
  '[ConstructorItem] Upsert ConstructorItem',
  props<{ constructorItem: ConstructorItem }>()
);

export const addConstructorItems = createAction(
  '[ConstructorItem] Add ConstructorItems',
  props<{ constructorItems: ConstructorItem[] }>()
);

export const upsertConstructorItems = createAction(
  '[ConstructorItem] Upsert ConstructorItems',
  props<{ constructorItems: ConstructorItem[] }>()
);

export const updateConstructorItem = createAction(
  '[ConstructorItem] Update ConstructorItem',
  props<{ constructorItem: Update<ConstructorItem> }>()
);

export const updateConstructorItems = createAction(
  '[ConstructorItem] Update ConstructorItems',
  props<{ constructorItems: Update<ConstructorItem>[] }>()
);

export const deleteConstructorItem = createAction(
  '[ConstructorItem] Delete ConstructorItem',
  props<{ id: string }>()
);

export const deleteConstructorItems = createAction(
  '[ConstructorItem] Delete ConstructorItems',
  props<{ ids: string[] }>()
);

export const clearConstructorItems = createAction(
  '[ConstructorItem] Clear ConstructorItems'
);

export const searchConstructorItems = createAction(
  '[ConstructorItem/API] Search ConstructorItems',
  props<{ series: string, season: number }>()
);

export const searchConstructorItemsSuccess = createAction(
  '[ConstructorItem/API] Search ConstructorItems Success',
  props<{ data: ConstructorItem[]}>()
);

export const searchConstructorItemsFailure = createAction(
  '[ConstructorItem/API] Search ConstructorItems Failure',
  props<{ error: any }>()
);
