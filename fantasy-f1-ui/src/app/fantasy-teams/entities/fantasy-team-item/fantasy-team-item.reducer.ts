import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { FantasyTeamItem } from './fantasy-team-item.model';
import * as FantasyTeamItemActions from './fantasy-team-item.actions';

export const fantasyTeamItemsFeatureKey = 'fantasyTeamItems';

export interface State extends EntityState<FantasyTeamItem> {
  // additional entities state properties
  myTeamId: string;
  loading: boolean;
  loadingMyTeam: boolean;
  saving: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<FantasyTeamItem> = createEntityAdapter<FantasyTeamItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  myTeamId: null,
  loading: false,
  loadingMyTeam: false,
  saving: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(FantasyTeamItemActions.addFantasyTeamItem,
    (state, action) => adapter.addOne(action.fantasyTeamItem, state)
  ),
  on(FantasyTeamItemActions.upsertFantasyTeamItem,
    (state, action) => adapter.upsertOne(action.fantasyTeamItem, state)
  ),
  on(FantasyTeamItemActions.addFantasyTeamItems,
    (state, action) => adapter.addMany(action.fantasyTeamItems, state)
  ),
  on(FantasyTeamItemActions.upsertFantasyTeamItems,
    (state, action) => adapter.upsertMany(action.fantasyTeamItems, state)
  ),
  on(FantasyTeamItemActions.updateFantasyTeamItem,
    (state, action) => adapter.updateOne(action.fantasyTeamItem, state)
  ),
  on(FantasyTeamItemActions.updateFantasyTeamItems,
    (state, action) => adapter.updateMany(action.fantasyTeamItems, state)
  ),
  on(FantasyTeamItemActions.deleteFantasyTeamItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(FantasyTeamItemActions.deleteFantasyTeamItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(FantasyTeamItemActions.loadFantasyTeamItems,
    (state, action) => adapter.setAll(action.fantasyTeamItems, state)
  ),
  on(FantasyTeamItemActions.clearFantasyTeamItems,
    state => adapter.removeAll(state)
  ),
  on(FantasyTeamItemActions.searchFantasyTeamItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(FantasyTeamItemActions.searchFantasyTeamItemsSuccess,
    (state, action) => adapter.upsertMany(action.data, {
      ...state,
      loading: false,
      error: null
    })),
  on(FantasyTeamItemActions.searchFantasyTeamItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
  on(FantasyTeamItemActions.searchMyFantasyTeamItem, (state, action) => {
    return {...state, loadingMyTeam: true, error: null}
  }),
  on(FantasyTeamItemActions.searchMyFantasyTeamItemSuccess,
    (state, action) => {
      let newState = {
        ...state,
        myTeamId: action.myTeam ? action.myTeam.id : null,
        loadingMyTeam: false,
        error: null
      };

      if (!action.myTeam) {
        return newState;
      }

      return adapter.upsertOne(action.myTeam, newState);
    }),

  on(FantasyTeamItemActions.searchMyFantasyTeamItemFailure, (state, action) => {
    return {...state, loadingMyTeam: false, myTeamId: null, error: action.error}
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

export const selectMyTeamId = (state: State) => state.myTeamId;

