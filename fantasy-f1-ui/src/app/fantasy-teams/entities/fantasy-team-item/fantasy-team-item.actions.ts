import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { FantasyTeamItem } from './fantasy-team-item.model';

export const loadFantasyTeamItems = createAction(
  '[FantasyTeamItem] Load FantasyTeamItems',
  props<{ fantasyTeamItems: FantasyTeamItem[] }>()
);

export const addFantasyTeamItem = createAction(
  '[FantasyTeamItem] Add FantasyTeamItem',
  props<{ fantasyTeamItem: FantasyTeamItem }>()
);

export const upsertFantasyTeamItem = createAction(
  '[FantasyTeamItem] Upsert FantasyTeamItem',
  props<{ fantasyTeamItem: FantasyTeamItem }>()
);

export const addFantasyTeamItems = createAction(
  '[FantasyTeamItem] Add FantasyTeamItems',
  props<{ fantasyTeamItems: FantasyTeamItem[] }>()
);

export const upsertFantasyTeamItems = createAction(
  '[FantasyTeamItem] Upsert FantasyTeamItems',
  props<{ fantasyTeamItems: FantasyTeamItem[] }>()
);

export const updateFantasyTeamItem = createAction(
  '[FantasyTeamItem] Update FantasyTeamItem',
  props<{ fantasyTeamItem: Update<FantasyTeamItem> }>()
);

export const updateFantasyTeamItems = createAction(
  '[FantasyTeamItem] Update FantasyTeamItems',
  props<{ fantasyTeamItems: Update<FantasyTeamItem>[] }>()
);

export const deleteFantasyTeamItem = createAction(
  '[FantasyTeamItem] Delete FantasyTeamItem',
  props<{ id: string }>()
);

export const deleteFantasyTeamItems = createAction(
  '[FantasyTeamItem] Delete FantasyTeamItems',
  props<{ ids: string[] }>()
);

export const clearFantasyTeamItems = createAction(
  '[FantasyTeamItem] Clear FantasyTeamItems'
);

export const searchFantasyTeamItems = createAction(
  '[FantasyTeamItem/API] Search FantasyTeamItems',
  props<{ series: string, season: number }>()
);

export const searchFantasyTeamItemsSuccess = createAction(
  '[FantasyTeamItem/API] Search FantasyTeamItems Success',
  props<{ data: FantasyTeamItem[] }>()
);

export const searchFantasyTeamItemsFailure = createAction(
  '[FantasyTeamItem/API] Search FantasyTeamItems Failure',
  props<{ error: any }>()
);

export const searchMyFantasyTeamItem = createAction(
  '[FantasyTeamItem/API] Search MyFantasyTeamItem',
  props<{ series: string, season: number }>()
);

export const searchMyFantasyTeamItemSuccess = createAction(
  '[FantasyTeamItem/API] Search MyFantasyTeamItem Success',
  props<{ myTeam: FantasyTeamItem }>()
);

export const searchMyFantasyTeamItemFailure = createAction(
  '[FantasyTeamItem/API] Search MyFantasyTeamItem Failure',
  props<{ error: any }>()
);
