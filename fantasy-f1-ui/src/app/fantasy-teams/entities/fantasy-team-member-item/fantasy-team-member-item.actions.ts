import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { FantasyTeamMemberItem } from './fantasy-team-member-item.model';

export const loadFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem] Load FantasyTeamMemberItems',
  props<{ fantasyTeamMemberItems: FantasyTeamMemberItem[] }>()
);

export const addFantasyTeamMemberItem = createAction(
  '[FantasyTeamMemberItem] Add FantasyTeamMemberItem',
  props<{ fantasyTeamMemberItem: FantasyTeamMemberItem }>()
);

export const upsertFantasyTeamMemberItem = createAction(
  '[FantasyTeamMemberItem] Upsert FantasyTeamMemberItem',
  props<{ fantasyTeamMemberItem: FantasyTeamMemberItem }>()
);

export const addFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem] Add FantasyTeamMemberItems',
  props<{ fantasyTeamMemberItems: FantasyTeamMemberItem[] }>()
);

export const upsertFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem] Upsert FantasyTeamMemberItems',
  props<{ fantasyTeamMemberItems: FantasyTeamMemberItem[] }>()
);

export const updateFantasyTeamMemberItem = createAction(
  '[FantasyTeamMemberItem] Update FantasyTeamMemberItem',
  props<{ fantasyTeamMemberItem: Update<FantasyTeamMemberItem> }>()
);

export const updateFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem] Update FantasyTeamMemberItems',
  props<{ fantasyTeamMemberItems: Update<FantasyTeamMemberItem>[] }>()
);

export const deleteFantasyTeamMemberItem = createAction(
  '[FantasyTeamMemberItem] Delete FantasyTeamMemberItem',
  props<{ id: string }>()
);

export const deleteFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem] Delete FantasyTeamMemberItems',
  props<{ ids: string[] }>()
);

export const clearFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem] Clear FantasyTeamMemberItems'
);

export const searchMyFantasyTeamMemberItems = createAction(
  '[FantasyTeamMemberItem/API] Search MyFantasyTeamMemberItems',
  props<{ series: string, season: number, race: number }>()
);

export const searchMyFantasyTeamMemberItemsSuccess = createAction(
  '[FantasyTeamMemberItem/API] Search MyFantasyTeamMemberItems Success',
  props<{ data: FantasyTeamMemberItem[] }>()
);

export const searchMyFantasyTeamMemberItemsFailure = createAction(
  '[FantasyTeamMemberItem/API] Search MyFantasyTeamMemberItems Failure',
  props<{ error: any }>()
);

export const setMyFantasyTeamMemberItem = createAction(
  '[FantasyTeamMemberItem/API] Set MyFantasyTeamMemberItem',
  props<{ series: string, season: number, id: string, teamMemberCategoryType: string }>()
);

export const setMyFantasyTeamMemberItemSuccess = createAction(
  '[FantasyTeamMemberItem/API] Set MyFantasyTeamMemberItem Success',
  props<{ data: FantasyTeamMemberItem }>()
);

export const setMyFantasyTeamMemberItemFailure = createAction(
  '[FantasyTeamMemberItem/API] Set MyFantasyTeamMemberItem Failure',
  props<{ error: any }>()
);


export const deleteMyFantasyTeamMemberItem = createAction(
  '[FantasyTeamMemberItem/API] Delete MyFantasyTeamMemberItem',
  props<{ series: string, season: number, teamMemberCategoryType: string }>()
);

export const deleteMyFantasyTeamMemberItemSuccess = createAction(
  '[FantasyTeamMemberItem/API] Delete MyFantasyTeamMemberItem Success',
  props<{ data: FantasyTeamMemberItem }>()
);

export const deleteMyFantasyTeamMemberItemFailure = createAction(
  '[FantasyTeamMemberItem/API] Delete MyFantasyTeamMemberItem Failure',
  props<{ error: any }>()
);
