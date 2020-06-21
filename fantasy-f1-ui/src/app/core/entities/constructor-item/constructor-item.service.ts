import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { ConstructorItem } from "./constructor-item.model";

@Injectable({
  providedIn: 'root'
})
export class ConstructorItemService {

  constructor(protected http: HttpClient) { }

  searchConstructorItems(series: string, season: number): Observable<ConstructorItem[]> {
    return this.http.get<ConstructorItem[]>(`/api/v1/original/${series}/constructors/${season}`);
  }
}
