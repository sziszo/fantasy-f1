import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as RaceItemActions from "./race-item.actions";
import { catchError, map, switchMap } from "rxjs/operators";
import { of } from "rxjs";
import { RaceItemService } from "./race-item.service";



@Injectable()
export class RaceItemEffects {

  loadRaceItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(RaceItemActions.searchRaceItems),
      switchMap(({series, season}) =>
        this.raceItemService.searchRaceItems(series, season).pipe(
          map(data => RaceItemActions.searchRaceItemsSuccess({
            raceItems: data.fantasyRaces,
            selectedRaceItemId: data.selectedFantasyRaceId
          })),
          catchError(error => of(RaceItemActions.searchRaceItemsFailure({error}))))
      )
    );
  });


  constructor(private actions$: Actions, private raceItemService: RaceItemService ) {}

}
