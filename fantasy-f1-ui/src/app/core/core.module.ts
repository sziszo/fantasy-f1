import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import * as fromCores from './reducers';
import { HomeComponent } from "./home/home.component";
import { SeriesSelectorComponent } from "./series-selector/series-selector.component";
import { SeasonSelectorComponent } from "./season-selector/season-selector.component";
import { MaterialModule } from "../material/material.module";


@NgModule({
  declarations: [
    HomeComponent,
    SeriesSelectorComponent,
    SeasonSelectorComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    StoreModule.forFeature(fromCores.commonsFeatureKey, fromCores.reducers, {metaReducers: fromCores.metaReducers})
  ],
  exports: [
    HomeComponent,
    SeriesSelectorComponent,
    SeasonSelectorComponent
  ]
})
export class CoreModule {
}
