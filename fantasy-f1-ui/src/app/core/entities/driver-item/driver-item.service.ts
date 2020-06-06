import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { DriverItem } from "./driver-item.model";

@Injectable({
  providedIn: 'root'
})
export class DriverItemService {

  constructor(protected http: HttpClient) {
  }

  searchDriverItems(series: string, season: number): Observable<DriverItem[]> {
    return this.http.get<DriverItem[]>(`/api/v1/original/${series}/drivers/${season}`);
  }
}
