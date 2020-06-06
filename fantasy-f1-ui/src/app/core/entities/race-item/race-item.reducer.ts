import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { RaceItem } from './race-item.model';
import * as RaceItemActions from './race-item.actions';

export const raceItemsFeatureKey = 'raceItems';

export interface State extends EntityState<RaceItem> {
  // additional entities state properties
  selectedRaceId: string;
  loading: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<RaceItem> = createEntityAdapter<RaceItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  selectedRaceId: null,
  loading: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(RaceItemActions.addRaceItem,
    (state, action) => adapter.addOne(action.raceItem, state)
  ),
  on(RaceItemActions.upsertRaceItem,
    (state, action) => adapter.upsertOne(action.raceItem, state)
  ),
  on(RaceItemActions.addRaceItems,
    (state, action) => adapter.addMany(action.raceItems, state)
  ),
  on(RaceItemActions.upsertRaceItems,
    (state, action) => adapter.upsertMany(action.raceItems, state)
  ),
  on(RaceItemActions.updateRaceItem,
    (state, action) => adapter.updateOne(action.raceItem, state)
  ),
  on(RaceItemActions.updateRaceItems,
    (state, action) => adapter.updateMany(action.raceItems, state)
  ),
  on(RaceItemActions.deleteRaceItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(RaceItemActions.deleteRaceItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(RaceItemActions.loadRaceItems,
    (state, action) => adapter.setAll(action.raceItems, state)
  ),
  on(RaceItemActions.clearRaceItems,
    state => adapter.removeAll(state)
  ),
  on(RaceItemActions.searchRaceItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(RaceItemActions.searchRaceItemsSuccess, (state, action) => {
    return adapter.setAll(action.raceItems, {
      ...state, loading: false, error: null, selectedRaceId: action.selectedRaceItemId
    });
  }),
  on(RaceItemActions.searchRaceItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();

export const selectedRaceId = (state: State) => state.selectedRaceId;
