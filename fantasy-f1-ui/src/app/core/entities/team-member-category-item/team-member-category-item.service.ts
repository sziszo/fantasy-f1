import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TeamMemberCategoryItem } from "./team-member-category-item.model";

@Injectable({
  providedIn: 'root'
})
export class TeamMemberCategoryItemService {

  constructor(protected http: HttpClient) {
  }

  searchTeamMemberCategoryItems(): Observable<TeamMemberCategoryItem[]> {
    return this.http.get<TeamMemberCategoryItem[]>(`/api/v1/fantasy/defs/fantasy/team/member/categories`);
  }

}
