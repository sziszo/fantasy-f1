import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { FantasyTeamItem } from "./fantasy-team-item.model";

@Injectable({
  providedIn: 'root'
})
export class FantasyTeamItemService {

  constructor(protected http: HttpClient) {
  }

  searchFantasyTeamItems(series: string, season: number): Observable<FantasyTeamItem[]> {
    return this.http.get<FantasyTeamItem[]>(`/api/v1/fantasy/${series}/teams/${season}/all`);
  }

  searchMyFantasyTeamItem(series: string, season: number): Observable<FantasyTeamItem> {
    return this.http.get<FantasyTeamItem>(`/api/v1/fantasy/${series}/teams/${season}/`);
  }
}
