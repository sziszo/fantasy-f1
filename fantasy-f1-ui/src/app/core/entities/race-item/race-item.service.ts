import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { FantasyRaceDefinition } from "./race-item.model";

@Injectable({
  providedIn: 'root'
})
export class RaceItemService {

  constructor(protected http: HttpClient) {
  }

  searchRaceItems(series: string, season: number): Observable<FantasyRaceDefinition> {
    return this.http.get<FantasyRaceDefinition>(`/api/v1/fantasy/defs/series/${series}/seasons/${season}/races`);
  }
}
