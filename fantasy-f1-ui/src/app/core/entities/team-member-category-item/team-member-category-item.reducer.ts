import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { TeamMemberCategoryItem } from './team-member-category-item.model';
import * as TeamMemberCategoryItemActions from './team-member-category-item.actions';


export const teamMemberCategoryItemsFeatureKey = 'teamMemberCategoryItems';

export interface State extends EntityState<TeamMemberCategoryItem> {
  // additional entities state properties
  loading: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<TeamMemberCategoryItem> = createEntityAdapter<TeamMemberCategoryItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  loading: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(TeamMemberCategoryItemActions.addTeamMemberCategoryItem,
    (state, action) => adapter.addOne(action.teamMemberCategoryItem, state)
  ),
  on(TeamMemberCategoryItemActions.upsertTeamMemberCategoryItem,
    (state, action) => adapter.upsertOne(action.teamMemberCategoryItem, state)
  ),
  on(TeamMemberCategoryItemActions.addTeamMemberCategoryItems,
    (state, action) => adapter.addMany(action.teamMemberCategoryItems, state)
  ),
  on(TeamMemberCategoryItemActions.upsertTeamMemberCategoryItems,
    (state, action) => adapter.upsertMany(action.teamMemberCategoryItems, state)
  ),
  on(TeamMemberCategoryItemActions.updateTeamMemberCategoryItem,
    (state, action) => adapter.updateOne(action.teamMemberCategoryItem, state)
  ),
  on(TeamMemberCategoryItemActions.updateTeamMemberCategoryItems,
    (state, action) => adapter.updateMany(action.teamMemberCategoryItems, state)
  ),
  on(TeamMemberCategoryItemActions.deleteTeamMemberCategoryItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(TeamMemberCategoryItemActions.deleteTeamMemberCategoryItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(TeamMemberCategoryItemActions.loadTeamMemberCategoryItems,
    (state, action) => adapter.setAll(action.teamMemberCategoryItems, state)
  ),
  on(TeamMemberCategoryItemActions.clearTeamMemberCategoryItems,
    state => adapter.removeAll(state)
  ),

  on(TeamMemberCategoryItemActions.searchTeamMemberCategoryItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(TeamMemberCategoryItemActions.searchTeamMemberCategoryItemsSuccess, (state, action) => {
    return adapter.setAll(action.data, {...state, loading: false, error: null})
  }),
  on(TeamMemberCategoryItemActions.searchTeamMemberCategoryItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();
