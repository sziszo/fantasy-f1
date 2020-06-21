import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { FantasyTeamMemberItem } from './fantasy-team-member-item.model';
import * as FantasyTeamMemberItemActions from './fantasy-team-member-item.actions';

export const fantasyTeamMemberItemsFeatureKey = 'fantasyTeamMemberItems';

export interface State extends EntityState<FantasyTeamMemberItem> {
  // additional entities state properties
  loading: boolean;
  saving: boolean;
  deleting: boolean;
  error: Error | null;

}

export const adapter: EntityAdapter<FantasyTeamMemberItem> = createEntityAdapter<FantasyTeamMemberItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  loading: false,
  saving: false,
  deleting: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(FantasyTeamMemberItemActions.addFantasyTeamMemberItem,
    (state, action) => adapter.addOne(action.fantasyTeamMemberItem, state)
  ),
  on(FantasyTeamMemberItemActions.upsertFantasyTeamMemberItem,
    (state, action) => adapter.upsertOne(action.fantasyTeamMemberItem, state)
  ),
  on(FantasyTeamMemberItemActions.addFantasyTeamMemberItems,
    (state, action) => adapter.addMany(action.fantasyTeamMemberItems, state)
  ),
  on(FantasyTeamMemberItemActions.upsertFantasyTeamMemberItems,
    (state, action) => adapter.upsertMany(action.fantasyTeamMemberItems, state)
  ),
  on(FantasyTeamMemberItemActions.updateFantasyTeamMemberItem,
    (state, action) => adapter.updateOne(action.fantasyTeamMemberItem, state)
  ),
  on(FantasyTeamMemberItemActions.updateFantasyTeamMemberItems,
    (state, action) => adapter.updateMany(action.fantasyTeamMemberItems, state)
  ),
  on(FantasyTeamMemberItemActions.deleteFantasyTeamMemberItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(FantasyTeamMemberItemActions.deleteFantasyTeamMemberItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(FantasyTeamMemberItemActions.loadFantasyTeamMemberItems,
    (state, action) => adapter.setAll(action.fantasyTeamMemberItems, state)
  ),
  on(FantasyTeamMemberItemActions.clearFantasyTeamMemberItems,
    state => adapter.removeAll(state)
  ),
  on(FantasyTeamMemberItemActions.searchMyFantasyTeamMemberItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(FantasyTeamMemberItemActions.searchMyFantasyTeamMemberItemsSuccess, (state, action) => {
    return adapter.upsertMany(action.data, {...state, loading: false, error: null})
  }),
  on(FantasyTeamMemberItemActions.searchMyFantasyTeamMemberItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
  on(FantasyTeamMemberItemActions.setMyFantasyTeamMemberItem, (state, action) => {
    return {...state, saving: true, error: null}
  }),
  on(FantasyTeamMemberItemActions.setMyFantasyTeamMemberItemSuccess, (state, action) => {
    return adapter.upsertOne(action.data, {...state, saving: false, error: null})
  }),
  on(FantasyTeamMemberItemActions.setMyFantasyTeamMemberItemFailure, (state, action) => {
    return {...state, saving: false, error: action.error}
  }),

  on(FantasyTeamMemberItemActions.deleteMyFantasyTeamMemberItem, (state, action) => {
    return {...state, deleting: true, error: null}
  }),
  on(FantasyTeamMemberItemActions.deleteMyFantasyTeamMemberItemSuccess, (state, action) => {
    return adapter.upsertOne(action.data, {...state, deleting: false, error: null})
  }),
  on(FantasyTeamMemberItemActions.deleteMyFantasyTeamMemberItemFailure, (state, action) => {
    return {...state, deleting: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();

export const isLoading = (state: State) => state.loading;
export const isSaving = (state: State) => state.saving;
