import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import * as TeamMemberCategoryItemActions from "./team-member-category-item.actions";
import { catchError, map, switchMap } from "rxjs/operators";
import { of } from "rxjs";
import { TeamMemberCategoryItemService } from "./team-member-category-item.service";


@Injectable()
export class TeamMemberCategoryItemEffects {

  loadTeamMemberCategoryItems$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(TeamMemberCategoryItemActions.searchTeamMemberCategoryItems),
      switchMap(() =>
        this.seriesItemService.searchTeamMemberCategoryItems().pipe(
          map(data => TeamMemberCategoryItemActions.searchTeamMemberCategoryItemsSuccess({data})),
          catchError(error => of(TeamMemberCategoryItemActions.searchTeamMemberCategoryItemsFailure({error}))))
      )
    );
  });

  constructor(private actions$: Actions, private seriesItemService: TeamMemberCategoryItemService) {
  }

}
