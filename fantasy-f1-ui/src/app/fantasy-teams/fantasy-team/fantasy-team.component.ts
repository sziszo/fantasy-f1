import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { FantasyTeamItem } from "../entities/fantasy-team-item/fantasy-team-item.model";
import { Store } from "@ngrx/store";
import { selectMyFantasyTeamItem } from "../entities/fantasy-team-item/fantasy-team-item.selector";
import { getSelectedSeasonItem } from "../../core/entities/season-item/season-item.selector";
import { searchMyFantasyTeamItem } from "../entities/fantasy-team-item/fantasy-team-item.actions";
import { searchTeamMemberCategoryItems } from "../../core/entities/team-member-category-item/team-member-category-item.actions";
import { DriverTableComponent } from "../../core/driver-table/driver-table.component";
import { ConstructorTableComponent } from "../../core/constructor-table/constructor-table.component";
import { selectAllTeamMemberConstructor } from "../entities/fantasy-team-member-item/fantasy-team-member-item.selector";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";

@Component({
  selector: 'app-fantasy-team',
  templateUrl: './fantasy-team.component.html',
  styleUrls: ['./fantasy-team.component.scss']
})
export class FantasyTeamComponent implements OnInit, AfterViewInit, OnDestroy {

  team: FantasyTeamItem;

  private unsubscribe$ = new Subject<void>();

  private _driverTableComponent: DriverTableComponent
  private _constructorTableComponent: ConstructorTableComponent

  private _constructorIds: string[];

  constructor(private store: Store) {
    store.select(selectMyFantasyTeamItem).subscribe(value => this.team = value)
  }

  ngOnInit(): void {
    this.store.dispatch(searchTeamMemberCategoryItems())

    this.store
      .select(getSelectedSeasonItem)
      .subscribe(value => {
        if (!value) {
          return;
        }

        this.store.dispatch(searchMyFantasyTeamItem({series: value.series, season: value.season}));
      })

    this.store
      .select(selectAllTeamMemberConstructor)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(value => {
        this._constructorIds = value;
        this.refreshDriverAndConstructorTable()
      });
  }

  ngAfterViewInit() {
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  onActivate(componentRef) {
    if (componentRef instanceof DriverTableComponent) {
      this._driverTableComponent = componentRef;
    } else if (componentRef instanceof ConstructorTableComponent) {
      this._constructorTableComponent = componentRef;
    }
    this.refreshDriverAndConstructorTable();
  }

  refreshDriverAndConstructorTable() {
    if (this._driverTableComponent) {
      this._driverTableComponent.constructorIds = this._constructorIds;
    }

    if (this._constructorTableComponent) {
      this._constructorTableComponent.constructorIds = this._constructorIds;
    }
  }
}
