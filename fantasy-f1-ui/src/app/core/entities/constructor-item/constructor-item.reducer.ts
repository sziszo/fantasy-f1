import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { ConstructorItem } from './constructor-item.model';
import * as ConstructorItemActions from './constructor-item.actions';

export const constructorItemsFeatureKey = 'constructorItems';

export interface State extends EntityState<ConstructorItem> {
  // additional entities state properties
  loading: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<ConstructorItem> = createEntityAdapter<ConstructorItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  loading: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(ConstructorItemActions.addConstructorItem,
    (state, action) => adapter.addOne(action.constructorItem, state)
  ),
  on(ConstructorItemActions.upsertConstructorItem,
    (state, action) => adapter.upsertOne(action.constructorItem, state)
  ),
  on(ConstructorItemActions.addConstructorItems,
    (state, action) => adapter.addMany(action.constructorItems, state)
  ),
  on(ConstructorItemActions.upsertConstructorItems,
    (state, action) => adapter.upsertMany(action.constructorItems, state)
  ),
  on(ConstructorItemActions.updateConstructorItem,
    (state, action) => adapter.updateOne(action.constructorItem, state)
  ),
  on(ConstructorItemActions.updateConstructorItems,
    (state, action) => adapter.updateMany(action.constructorItems, state)
  ),
  on(ConstructorItemActions.deleteConstructorItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(ConstructorItemActions.deleteConstructorItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(ConstructorItemActions.loadConstructorItems,
    (state, action) => adapter.setAll(action.constructorItems, state)
  ),
  on(ConstructorItemActions.clearConstructorItems,
    state => adapter.removeAll(state)
  ),
  on(ConstructorItemActions.searchConstructorItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(ConstructorItemActions.searchConstructorItemsSuccess, (state, action) => {
    return adapter.setAll(action.data, {...state, loading: false, error: null});
  }),
  on(ConstructorItemActions.searchConstructorItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();
