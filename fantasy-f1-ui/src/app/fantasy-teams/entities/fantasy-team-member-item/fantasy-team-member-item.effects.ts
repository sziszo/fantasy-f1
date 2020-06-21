import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as FantasyTeamMemberActions from "./fantasy-team-member-item.actions";
import { catchError, concatMap, map } from "rxjs/operators";
import { of } from "rxjs";
import { FantasyTeamMemberItemService } from "./fantasy-team-member-item.service";


@Injectable()
export class FantasyTeamMemberItemEffects {

  loadFantasyTeamMemberItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(FantasyTeamMemberActions.searchMyFantasyTeamMemberItems),
      concatMap(({series, season, race}) =>
        this.fantasyTeamMemberItemService.searchMyFantasyTeamMemberItems(series, season, race).pipe(
          map(data => FantasyTeamMemberActions.searchMyFantasyTeamMemberItemsSuccess({data})),
          catchError(error => of(FantasyTeamMemberActions.searchMyFantasyTeamMemberItemsFailure({error}))))
      )
    );
  });

  setFantasyTeamMemberItem$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(FantasyTeamMemberActions.setMyFantasyTeamMemberItem),
      concatMap(({series, season, id, teamMemberCategoryType}) =>
        this.fantasyTeamMemberItemService.setMyFantasyTeamMemberItem(series, season, id, teamMemberCategoryType).pipe(
          map(data => FantasyTeamMemberActions.setMyFantasyTeamMemberItemSuccess({data})),
          catchError(error => of(FantasyTeamMemberActions.setMyFantasyTeamMemberItemFailure({error}))))
      )
    );
  });

  deleteFantasyTeamMemberItem$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(FantasyTeamMemberActions.deleteMyFantasyTeamMemberItem),
      concatMap(({series, season, teamMemberCategoryType}) =>
        this.fantasyTeamMemberItemService.deleteMyFantasyTeamMemberItem(series, season, teamMemberCategoryType).pipe(
          map(data => FantasyTeamMemberActions.deleteMyFantasyTeamMemberItemSuccess({data})),
          catchError(error => of(FantasyTeamMemberActions.deleteMyFantasyTeamMemberItemFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private fantasyTeamMemberItemService: FantasyTeamMemberItemService) {
  }

}
