import {
  ActionReducer,
  ActionReducerMap,
  createFeatureSelector,
  createSelector,
  MetaReducer
} from '@ngrx/store';
import { environment } from '../../../environments/environment';
import * as fromFantasyTeamItem from '../entities/fantasy-team-item/fantasy-team-item.reducer';
import * as fromFantasyTeamMemberItem from '../entities/fantasy-team-member-item/fantasy-team-member-item.reducer';

export const fantasyTeamsFeatureKey = 'fantasyTeams';

export interface State {

  [fromFantasyTeamItem.fantasyTeamItemsFeatureKey]: fromFantasyTeamItem.State;
  [fromFantasyTeamMemberItem.fantasyTeamMemberItemsFeatureKey]: fromFantasyTeamMemberItem.State;

}

export const reducers: ActionReducerMap<State> = {

  [fromFantasyTeamItem.fantasyTeamItemsFeatureKey]: fromFantasyTeamItem.reducer,
  [fromFantasyTeamMemberItem.fantasyTeamMemberItemsFeatureKey]: fromFantasyTeamMemberItem.reducer,

};


export const metaReducers: MetaReducer<State>[] = !environment.production ? [] : [];


export const selectFantasyTeamsState = createFeatureSelector<State>(fantasyTeamsFeatureKey);

