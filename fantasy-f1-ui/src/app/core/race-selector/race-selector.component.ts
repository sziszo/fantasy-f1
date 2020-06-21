import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from "rxjs";
import { RaceItem } from "../entities/race-item/race-item.model";

@Component({
  selector: 'app-race-selector',
  templateUrl: './race-selector.component.html',
  styleUrls: ['./race-selector.component.scss']
})
export class RaceSelectorComponent implements OnInit {

  @Output()
  selectionChange = new EventEmitter<string>();

  @Input()
  raceItems$: Observable<RaceItem[]>;

  @Input()
  selectedRace;


  constructor() {
  }

  ngOnInit(): void {
  }

  onSelectionChanged(raceItemId: string) {
    this.selectionChange.emit(raceItemId);
  }

}
