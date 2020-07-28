import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import {
  DeleteTeamMemberResponse,
  FantasyTeamMemberItem,
  SetTeamMember,
  SetTeamMemberResponse
} from "./fantasy-team-member-item.model";

@Injectable({
  providedIn: 'root'
})
export class FantasyTeamMemberItemService {

  constructor(protected http: HttpClient) {
  }

  searchMyFantasyTeamMemberItems(series: string, season: number, race: number): Observable<FantasyTeamMemberItem[]> {
    return this.http.get<FantasyTeamMemberItem[]>(`/api/v1/fantasy/${series}/teams/${season}/members/${race}`);
  }

  setMyFantasyTeamMemberItem(series: string, season: number, id: string, teamMemberCategoryType: string): Observable<SetTeamMemberResponse> {
    return this.http.post<SetTeamMemberResponse>(`/api/v1/fantasy/${series}/teams/${season}/members`,
      <SetTeamMember>{
        id: id,
        teamMemberTypeId: teamMemberCategoryType
      });
  }

  deleteMyFantasyTeamMemberItem(series: string, season: number, teamMemberCategoryType: string): Observable<DeleteTeamMemberResponse> {
    return this.http.delete<DeleteTeamMemberResponse>(`/api/v1/fantasy/${series}/teams/${season}/members/${teamMemberCategoryType}`);
  }
}
