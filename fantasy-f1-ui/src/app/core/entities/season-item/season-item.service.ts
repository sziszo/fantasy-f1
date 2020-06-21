import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { FantasySeasonDefinitionItem } from "./season-item.model";

@Injectable({
  providedIn: 'root'
})
export class SeasonItemService {

  constructor(protected http: HttpClient) {
  }

  searchSeasonItems(series: string): Observable<FantasySeasonDefinitionItem> {
    return this.http.get<FantasySeasonDefinitionItem>(`/api/v1/fantasy/defs/series/${series}/seasons`);
  }
}
