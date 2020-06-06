import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StoreModule } from '@ngrx/store';
import * as fromCores from './reducers';
import { HomeComponent } from "./home/home.component";
import { SeriesSelectorComponent } from "./series-selector/series-selector.component";
import { SeasonSelectorComponent } from "./season-selector/season-selector.component";
import { MaterialModule } from "../material/material.module";
import { EffectsModule } from '@ngrx/effects';
import { SeriesItemEffects } from './entities/series-item/series-item.effects';
import { SeasonItemEffects } from './entities/season-item/season-item.effects';
import { RaceItemEffects } from './entities/race-item/race-item.effects';
import { RaceSelectorComponent } from './race-selector/race-selector.component';
import { FlexLayoutModule } from "@angular/flex-layout";
import { DriverItemEffects } from './entities/driver-item/driver-item.effects';
import { ConstructorItemEffects } from './entities/constructor-item/constructor-item.effects';
import { DriverTableComponent } from './driver-table/driver-table.component';
import { ConstructorTableComponent } from './constructor-table/constructor-table.component';
import { TeamMemberCategoryItemEffects } from "./entities/team-member-category-item/team-member-category-item.effects";
import { TeamMemberActionSelectorComponent } from "./team-member-action-selector/team-member-action-selector.component";


@NgModule({
  declarations: [
    HomeComponent,
    SeriesSelectorComponent,
    SeasonSelectorComponent,
    RaceSelectorComponent,
    DriverTableComponent,
    ConstructorTableComponent,
    TeamMemberActionSelectorComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule,
    StoreModule.forFeature(fromCores.commonsFeatureKey, fromCores.reducers, {metaReducers: fromCores.metaReducers}),
    EffectsModule.forFeature([SeriesItemEffects, SeasonItemEffects, RaceItemEffects, DriverItemEffects, ConstructorItemEffects, TeamMemberCategoryItemEffects]),

  ],
  exports: [
    HomeComponent,
    SeriesSelectorComponent,
    SeasonSelectorComponent,
    RaceSelectorComponent,
    DriverTableComponent,
    ConstructorTableComponent
  ]
})
export class CoreModule {
}
