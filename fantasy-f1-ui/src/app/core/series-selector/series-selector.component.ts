import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from "rxjs";
import { SeriesItem } from "../entities/series-item/series-item.model";
import { Store } from "@ngrx/store";
import { selectAllSeriesItems } from "../entities/series-item/series-item.selector";
import { loadSeriesItems, searchSeriesItems } from "../entities/series-item/series-item.actions";

@Component({
  selector: 'app-series-selector',
  templateUrl: './series-selector.component.html',
  styleUrls: ['./series-selector.component.scss']
})
export class SeriesSelectorComponent implements OnInit {

  @Output()
  selectionChange = new EventEmitter<string>();

  @Input()
  selectedSeries;

  seriesItems$: Observable<SeriesItem[]>;

  constructor(private store: Store) {
    this.seriesItems$ = store.select(selectAllSeriesItems)
  }

  ngOnInit(): void {

    this.store.dispatch(searchSeriesItems());

    // this.store.dispatch(loadSeriesItems({
    //   seriesItems: [
    //     {id: "f1", name: "Formula 1"},
    //     {id: "fe", name: "Formula E"}
    //   ]
    // }))
    //
    // if (!this.selectedSeries) {
    //   this.selectedSeries = 'f1';
    // }
    // this.onSelectionChanged(this.selectedSeries);
  }

  onSelectionChanged(seriesItemId: string) {
    this.selectionChange.emit(seriesItemId)
    // this.store.dispatch(setSelectedSeriesItem({selectedSeriesItemId: seriesItemId}))
  }

}
