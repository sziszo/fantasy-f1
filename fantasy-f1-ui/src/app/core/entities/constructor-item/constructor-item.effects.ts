import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as ConstructorItemActions from "./constructor-item.actions";
import { catchError, map, switchMap } from "rxjs/operators";
import { of } from "rxjs";
import { ConstructorItemService } from "./constructor-item.service";



@Injectable()
export class ConstructorItemEffects {

  loadConstructorItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(ConstructorItemActions.searchConstructorItems),
      switchMap(({series, season}) =>
        this.constructorItemService.searchConstructorItems(series, season).pipe(
          map(data => ConstructorItemActions.searchConstructorItemsSuccess({data})),
          catchError(error => of(ConstructorItemActions.searchConstructorItemsFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private constructorItemService: ConstructorItemService) {}

}
