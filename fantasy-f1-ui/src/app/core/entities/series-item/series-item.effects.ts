import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as SeriesItemActions from "./series-item.actions";
import { catchError, map, switchMap } from "rxjs/operators";
import { of } from "rxjs";
import { SeriesItemService } from "./series-item.service";


@Injectable()
export class SeriesItemEffects {

  loadSeriesItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(SeriesItemActions.searchSeriesItems),
      switchMap(() =>
        this.seriesItemService.searchSeriesItems().pipe(
          map(data => SeriesItemActions.searchSeriesItemsSuccess({
            seriesItems: data.fantasySeriesList,
            selectedSeriesItemId: data.selectedFantasySeriesId
          })),
          catchError(error => of(SeriesItemActions.searchSeriesItemsFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private seriesItemService: SeriesItemService) {
  }

}
