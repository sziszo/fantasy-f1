import { createAction, props } from '@ngrx/store';
import { Update } from '@ngrx/entity';

import { TeamMemberCategoryItem } from './team-member-category-item.model';

export const loadTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem] Load TeamMemberCategoryItems',
  props<{ teamMemberCategoryItems: TeamMemberCategoryItem[] }>()
);

export const addTeamMemberCategoryItem = createAction(
  '[TeamMemberCategoryItem] Add TeamMemberCategoryItem',
  props<{ teamMemberCategoryItem: TeamMemberCategoryItem }>()
);

export const upsertTeamMemberCategoryItem = createAction(
  '[TeamMemberCategoryItem] Upsert TeamMemberCategoryItem',
  props<{ teamMemberCategoryItem: TeamMemberCategoryItem }>()
);

export const addTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem] Add TeamMemberCategoryItems',
  props<{ teamMemberCategoryItems: TeamMemberCategoryItem[] }>()
);

export const upsertTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem] Upsert TeamMemberCategoryItems',
  props<{ teamMemberCategoryItems: TeamMemberCategoryItem[] }>()
);

export const updateTeamMemberCategoryItem = createAction(
  '[TeamMemberCategoryItem] Update TeamMemberCategoryItem',
  props<{ teamMemberCategoryItem: Update<TeamMemberCategoryItem> }>()
);

export const updateTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem] Update TeamMemberCategoryItems',
  props<{ teamMemberCategoryItems: Update<TeamMemberCategoryItem>[] }>()
);

export const deleteTeamMemberCategoryItem = createAction(
  '[TeamMemberCategoryItem] Delete TeamMemberCategoryItem',
  props<{ id: string }>()
);

export const deleteTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem] Delete TeamMemberCategoryItems',
  props<{ ids: string[] }>()
);

export const clearTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem] Clear TeamMemberCategoryItems'
);

export const searchTeamMemberCategoryItems = createAction(
  '[TeamMemberCategoryItem/API] Search TeamMemberCategoryItems'
);

export const searchTeamMemberCategoryItemsSuccess = createAction(
  '[TeamMemberCategoryItem/API] Search TeamMemberCategoryItems Success',
  props<{ data: TeamMemberCategoryItem[] }>()
);

export const searchTeamMemberCategoryItemsFailure = createAction(
  '[TeamMemberCategoryItem/API] Search TeamMemberCategoryItems Failure',
  props<{ error: Error }>()
);
