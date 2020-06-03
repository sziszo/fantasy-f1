import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { SeriesItem } from "../entities/series-item/series-item.model";
import { Store } from "@ngrx/store";
import { getSelectedSeriesId, selectAllSeriesItems } from "../entities/series-item/series-item.selector";
import { loadSeriesItems, setSelectedSeriesItem } from "../entities/series-item/series-item.actions";

@Component({
  selector: 'app-series-selector',
  templateUrl: './series-selector.component.html',
  styleUrls: ['./series-selector.component.scss']
})
export class SeriesSelectorComponent implements OnInit {

  seriesItems$: Observable<SeriesItem[]>;
  selectedSeries = '';

  constructor(private store: Store) {
    this.seriesItems$ = store.select(selectAllSeriesItems)

    this.store
      .select(getSelectedSeriesId)
      .subscribe(value => this.selectedSeries = value)

  }

  ngOnInit(): void {

    this.store.dispatch(loadSeriesItems({
      seriesItems: [
        {id: "f1", name: "Formula 1"},
        {id: "fe", name: "Formula E"}
      ]
    }))

    this.onSeriesChanged('f1');
  }

  onSeriesChanged(seriesItemId: string) {
    this.store.dispatch(setSelectedSeriesItem({selectedSeriesItemId: seriesItemId}))
  }

}
