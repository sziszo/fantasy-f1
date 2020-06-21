import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as DriverItemActions from "./driver-item.actions";
import { catchError, map, switchMap } from "rxjs/operators";
import { of } from "rxjs";
import { DriverItemService } from "./driver-item.service";


@Injectable()
export class DriverItemEffects {

  loadDriverItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(DriverItemActions.searchDriverItems),
      switchMap(({series, season}) =>
        this.driverItemService.searchDriverItems(series, season).pipe(
          map(data => DriverItemActions.searchDriverItemsSuccess({data})),
          catchError(error => of(DriverItemActions.searchDriverItemsFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private driverItemService: DriverItemService) {
  }

}
