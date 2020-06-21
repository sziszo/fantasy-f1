import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { DriverItem } from './driver-item.model';
import * as DriverItemActions from './driver-item.actions';

export const driverItemsFeatureKey = 'driverItems';

export interface State extends EntityState<DriverItem> {
  // additional entities state properties
  loading: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<DriverItem> = createEntityAdapter<DriverItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  loading: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(DriverItemActions.addDriverItem,
    (state, action) => adapter.addOne(action.driverItem, state)
  ),
  on(DriverItemActions.upsertDriverItem,
    (state, action) => adapter.upsertOne(action.driverItem, state)
  ),
  on(DriverItemActions.addDriverItems,
    (state, action) => adapter.addMany(action.driverItems, state)
  ),
  on(DriverItemActions.upsertDriverItems,
    (state, action) => adapter.upsertMany(action.driverItems, state)
  ),
  on(DriverItemActions.updateDriverItem,
    (state, action) => adapter.updateOne(action.driverItem, state)
  ),
  on(DriverItemActions.updateDriverItems,
    (state, action) => adapter.updateMany(action.driverItems, state)
  ),
  on(DriverItemActions.deleteDriverItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(DriverItemActions.deleteDriverItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(DriverItemActions.loadDriverItems,
    (state, action) => adapter.setAll(action.driverItems, state)
  ),
  on(DriverItemActions.clearDriverItems,
    state => adapter.removeAll(state)
  ),
  on(DriverItemActions.searchDriverItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(DriverItemActions.searchDriverItemsSuccess, (state, action) => {
    return adapter.setAll(action.data, {...state, loading: false, error: null});
  }),
  on(DriverItemActions.searchDriverItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();
