import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { FantasySeriesDefinitionItem, SeriesItem } from "./series-item.model";

@Injectable({
  providedIn: 'root'
})
export class SeriesItemService {

  constructor(protected http: HttpClient) {
  }

  searchSeriesItems(): Observable<FantasySeriesDefinitionItem> {
    return this.http.get<FantasySeriesDefinitionItem>(`/api/v1/fantasy/defs/series`);
  }
}
