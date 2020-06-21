import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as SeasonItemActions from "./season-item.actions";
import { catchError, map, switchMap } from "rxjs/operators";
import { of } from "rxjs";
import { SeasonItemService } from "./season-item.service";



@Injectable()
export class SeasonItemEffects {

  loadSeasonItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(SeasonItemActions.searchSeasonItems),
      switchMap(({series}) =>
        this.seasonItemService.searchSeasonItems(series).pipe(
          map(data => SeasonItemActions.searchSeasonItemsSuccess({
            seasonItems: data.fantasySeasons,
            selectedSeasonItemId: data.selectedFantasySeasonId
          })),
          catchError(error => of(SeasonItemActions.searchSeasonItemsFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private seasonItemService: SeasonItemService) {}

}
