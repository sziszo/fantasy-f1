import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from "rxjs";
import { SeasonItem } from "../entities/season-item/season-item.model";


@Component({
  selector: 'app-season-selector',
  templateUrl: './season-selector.component.html',
  styleUrls: ['./season-selector.component.scss']
})
export class SeasonSelectorComponent implements OnInit {

  @Output()
  selectionChange = new EventEmitter<string>();

  @Input()
  seasonItems$: Observable<SeasonItem[]>;

  @Input()
  selectedSeason;

  constructor() {
  }

  ngOnInit(): void {
  }

  onSelectionChanged(seasonItemId: string) {
    this.selectionChange.emit(seasonItemId);
  }

}
