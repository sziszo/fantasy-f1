import { createReducer, on } from '@ngrx/store';
import { createEntityAdapter, EntityAdapter, EntityState } from '@ngrx/entity';
import { SeriesItem } from './series-item.model';
import * as SeriesItemActions from './series-item.actions';

export const seriesItemsFeatureKey = 'seriesItems';

export interface State extends EntityState<SeriesItem> {
  // additional entities state properties
  selectedSeriesId: string;
  loading: boolean;
  error: Error | null;
}

export const adapter: EntityAdapter<SeriesItem> = createEntityAdapter<SeriesItem>();

export const initialState: State = adapter.getInitialState({
  // additional entity state properties
  selectedSeriesId: null,
  loading: false,
  error: null
});


export const reducer = createReducer(
  initialState,
  on(SeriesItemActions.addSeriesItem,
    (state, action) => adapter.addOne(action.seriesItem, state)
  ),
  on(SeriesItemActions.upsertSeriesItem,
    (state, action) => adapter.upsertOne(action.seriesItem, state)
  ),
  on(SeriesItemActions.addSeriesItems,
    (state, action) => adapter.addMany(action.seriesItems, state)
  ),
  on(SeriesItemActions.upsertSeriesItems,
    (state, action) => adapter.upsertMany(action.seriesItems, state)
  ),
  on(SeriesItemActions.updateSeriesItem,
    (state, action) => adapter.updateOne(action.seriesItem, state)
  ),
  on(SeriesItemActions.updateSeriesItems,
    (state, action) => adapter.updateMany(action.seriesItems, state)
  ),
  on(SeriesItemActions.deleteSeriesItem,
    (state, action) => adapter.removeOne(action.id, state)
  ),
  on(SeriesItemActions.deleteSeriesItems,
    (state, action) => adapter.removeMany(action.ids, state)
  ),
  on(SeriesItemActions.loadSeriesItems,
    (state, action) => adapter.setAll(action.seriesItems, state)
  ),
  on(SeriesItemActions.clearSeriesItems,
    state => adapter.removeAll(state)
  ),
  on(SeriesItemActions.setSelectedSeriesItem,
    (state, action) => {
      return {...state, selectedSeriesId: action.selectedSeriesItemId}
    }
  ),
  on(SeriesItemActions.searchSeriesItems, (state, action) => {
    return {...state, loading: true, error: null}
  }),
  on(SeriesItemActions.searchSeriesItemsSuccess, (state, action) => {
    return adapter.setAll(action.seriesItems, {...state, loading: false, error: null, selectedSeriesId: action.selectedSeriesItemId})
  }),
  on(SeriesItemActions.searchSeriesItemsFailure, (state, action) => {
    return {...state, loading: false, error: action.error}
  }),
);


export const {
  selectIds,
  selectEntities,
  selectAll,
  selectTotal,
} = adapter.getSelectors();

export const selectedSeriesId = (state: State) => state.selectedSeriesId;
