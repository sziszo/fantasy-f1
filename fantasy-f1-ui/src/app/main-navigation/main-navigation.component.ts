import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { Store } from "@ngrx/store";
import { searchSeasonItems, setSelectedSeasonItem } from "../core/entities/season-item/season-item.actions";
import { setSelectedSeriesItem } from "../core/entities/series-item/series-item.actions";
import { getSelectedSeriesId } from "../core/entities/series-item/series-item.selector";
import { getAllSeasonItems, getSelectedSeasonItem } from "../core/entities/season-item/season-item.selector";
import { SeasonItem } from "../core/entities/season-item/season-item.model";
import { RaceItem } from '../core/entities/race-item/race-item.model';
import { searchRaceItems, setSelectedRaceItem } from "../core/entities/race-item/race-item.actions";
import { getAllRaceItems, getSelectedRaceItem } from "../core/entities/race-item/race-item.selector";
import { searchConstructorItems } from "../core/entities/constructor-item/constructor-item.actions";
import { searchDriverItems } from "../core/entities/driver-item/driver-item.actions";


@Component({
  selector: 'app-main-navigation',
  templateUrl: './main-navigation.component.html',
  styleUrls: ['./main-navigation.component.scss']
})
export class MainNavigationComponent implements OnInit {

  opened: boolean = true;
  year = (new Date()).getFullYear();

  selectedSeries: string = null;

  selectedSeasonId: string = null;
  seasonItems$: Observable<SeasonItem[]>;
  season: number;

  raceItems$: Observable<RaceItem[]>;
  selectedRaceId = null;
  race: number;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, private store: Store) {
    this.store
      .select(getSelectedSeriesId)
      .subscribe(value => {
        this.selectedSeries = !value ? 'f1' : value;
        this.store.dispatch(searchSeasonItems({series: this.selectedSeries}));
      })

    this.seasonItems$ = store.select(getAllSeasonItems)

    this.store
      .select(getSelectedSeasonItem)
      .subscribe(value => {
        if (!value || this.selectedSeasonId == value.id) {
          return;
        }

        this.selectedSeasonId = value.id
        this.season = value.season;
        this.store.dispatch(searchRaceItems({series: this.selectedSeries, season: this.season}));
        this.store.dispatch(searchConstructorItems({series: this.selectedSeries, season: this.season}));
        this.store.dispatch(searchDriverItems({series: this.selectedSeries, season: this.season}));
      })

    this.raceItems$ = store.select(getAllRaceItems)

    this.store
      .select(getSelectedRaceItem)
      .subscribe(value => {
        if (!value) {
          return null;
        }
        this.selectedRaceId = value.id;
        this.race = value.round;
      })

  }

  ngOnInit(): void {
  }

  onSeriesChanged(seriesItemId: string) {
    this.store.dispatch(setSelectedSeriesItem({selectedSeriesItemId: seriesItemId}))
  }

  onSeasonChanged(seasonItemId: string) {
    this.store.dispatch(setSelectedSeasonItem({selectedSeasonItemId: seasonItemId}))

  }

  onRaceChanged(raceItemId: string) {
    this.store.dispatch(setSelectedRaceItem({selectedRaceItemId: raceItemId}))
  }

}
