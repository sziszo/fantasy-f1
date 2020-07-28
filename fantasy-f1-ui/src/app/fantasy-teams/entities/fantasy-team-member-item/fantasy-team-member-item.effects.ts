import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as FantasyTeamMemberActions from "./fantasy-team-member-item.actions";
import * as FantasyTeamActions from "../fantasy-team-item/fantasy-team-item.actions";
import { catchError, concatMap, map, switchMap } from "rxjs/operators";
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
        this.fantasyTeamMemberItemService.setMyFantasyTeamMemberItem(series, season, id, teamMemberCategoryType)
          .pipe(
            switchMap(data => [
              FantasyTeamMemberActions.setMyFantasyTeamMemberItemSuccess({data: data.fantasyTeamMemberItem}),
              FantasyTeamActions.upsertFantasyTeamItem({fantasyTeamItem: data.fantasyTeamItem})
            ]),
            catchError(error => of(FantasyTeamMemberActions.setMyFantasyTeamMemberItemFailure({error}))))
      )
    );
  });

  deleteFantasyTeamMemberItem$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(FantasyTeamMemberActions.deleteMyFantasyTeamMemberItem),
      concatMap(({series, season, teamMemberCategoryType}) =>
        this.fantasyTeamMemberItemService.deleteMyFantasyTeamMemberItem(series, season, teamMemberCategoryType)
          .pipe(
            switchMap(data => [
              FantasyTeamMemberActions.deleteMyFantasyTeamMemberItemSuccess({data: data.fantasyTeamMemberItem}),
              FantasyTeamActions.upsertFantasyTeamItem({fantasyTeamItem: data.fantasyTeamItem})
            ]),
            catchError(error => of(FantasyTeamMemberActions.deleteMyFantasyTeamMemberItemFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private fantasyTeamMemberItemService: FantasyTeamMemberItemService) {
  }

}
