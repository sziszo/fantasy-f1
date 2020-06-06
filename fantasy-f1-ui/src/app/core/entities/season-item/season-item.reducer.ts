import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { SeasonItem } from './season-item.model';
import * as SeasonItemActions from './season-item.actions';

export const seasonItemsFeatureKey = 'seasonItems';

export interface State extends EntityState<SeasonItem> {
  // additional entities state properties
  selectedSeasonId: string;
  loading: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<SeasonItem> = createEntityAdapter<SeasonItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  selectedSeasonId: null,
  loading: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(SeasonItemActions.addSeasonItem,
    (state, action) => adapter.addOne(action.seasonItem, state)
  ),
  on(SeasonItemActions.upsertSeasonItem,
    (state, action) => adapter.upsertOne(action.seasonItem, state)
  ),
  on(SeasonItemActions.addSeasonItems,
    (state, action) => adapter.addMany(action.seasonItems, state)
  ),
  on(SeasonItemActions.upsertSeasonItems,
    (state, action) => adapter.upsertMany(action.seasonItems, state)
  ),
  on(SeasonItemActions.updateSeasonItem,
    (state, action) => adapter.updateOne(action.seasonItem, state)
  ),
  on(SeasonItemActions.updateSeasonItems,
    (state, action) => adapter.updateMany(action.seasonItems, state)
  ),
  on(SeasonItemActions.deleteSeasonItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(SeasonItemActions.deleteSeasonItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(SeasonItemActions.loadSeasonItems,
    (state, action) => adapter.setAll(action.seasonItems, state)
  ),
  on(SeasonItemActions.clearSeasonItems,
    state => adapter.removeAll(state)
  ),
  on(SeasonItemActions.setSelectedSeasonItem,
    (state, action) => {
      return {...state, selectedSeasonId: action.selectedSeasonItemId}
    }
  ),
  on(SeasonItemActions.searchSeasonItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(SeasonItemActions.searchSeasonItemsSuccess, (state, action) => {
    return adapter.setAll(action.seasonItems, {
      ...state,
      loading: false,
      error: null,
      selectedSeasonId: action.selectedSeasonItemId
    });
  }),
  on(SeasonItemActions.searchSeasonItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();

export const selectedSeasonId = (state: State) => state.selectedSeasonId;
