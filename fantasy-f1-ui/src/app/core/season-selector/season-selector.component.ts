import { Component, Input, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { SeasonItem } from "../entities/season-item/season-item.model";
import { getSelectedSeasonId, selectAllSeasonItems } from "../entities/season-item/season-item.selector";
import { loadSeasonItems, setSelectedSeasonItem } from "../entities/season-item/season-item.actions";
import { Store } from "@ngrx/store";


@Component({
  selector: 'app-season-selector',
  templateUrl: './season-selector.component.html',
  styleUrls: ['./season-selector.component.scss']
})
export class SeasonSelectorComponent implements OnInit {

  seasonItems$: Observable<SeasonItem[]>;

  @Input()
  selectedSeason;

  constructor(private store: Store) {
    this.seasonItems$ = store.select(selectAllSeasonItems)

    this.store
      .select(getSelectedSeasonId)
      .subscribe(value => this.selectedSeason = value)
  }

  ngOnInit(): void {

    this.store.dispatch(loadSeasonItems({
      seasonItems: [
        {id: 2019, name: "2019"},
        {id: 2020, name: "2020"}
      ]
    }))

    if (!this.selectedSeason) {
      this.onSeasonChanged((new Date()).getFullYear());
    }

  }

  onSeasonChanged(seasonItemId: number) {
    this.store.dispatch(setSelectedSeasonItem({selectedSeasonItemId: seasonItemId}))
  }

}
