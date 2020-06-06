import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngrx/effects';
import * as FantasyTeamActions from "../fantasy-team-item/fantasy-team-item.actions";
import { catchError, concatMap, map } from "rxjs/operators";
import { of } from "rxjs";
import { FantasyTeamItemService } from "./fantasy-team-item.service";


@Injectable()
export class FantasyTeamItemEffects {

  @Effect()
  loadFantasyTeamItems$ = this.actions$.pipe(
    ofType(FantasyTeamActions.searchFantasyTeamItems),
    concatMap(({series, season}) =>
      this.fantasyTeamItemService.searchFantasyTeamItems(series, season).pipe(
        map(data => FantasyTeamActions.searchFantasyTeamItemsSuccess({data})),
        catchError(error => of(FantasyTeamActions.searchFantasyTeamItemsFailure({error}))))
    )
  );


  @Effect()
  loadMyFantasyTeamItem$ = this.actions$.pipe(
    ofType(FantasyTeamActions.searchMyFantasyTeamItem),
    concatMap(({series, season}) =>
      this.fantasyTeamItemService.searchMyFantasyTeamItem(series, season).pipe(
        map(data => FantasyTeamActions.searchMyFantasyTeamItemSuccess({myTeam: data})),
        catchError(error => of(FantasyTeamActions.searchFantasyTeamItemsFailure({error}))))
    )
  );


  constructor(private actions$: Actions, private fantasyTeamItemService: FantasyTeamItemService) {
  }

}
