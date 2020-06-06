import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FantasyTeamsRoutingModule } from './fantasy-teams-routing.module';
import { FantasyTeamsComponent } from './fantasy-team-table/fantasy-teams.component';
import { FantasyTeamComponent } from './fantasy-team/fantasy-team.component';
import { FantasyTeamMembersComponent } from './fantasy-team-members/fantasy-team-members.component';
import { FantasyTeamResultsComponent } from './fantasy-team-results/fantasy-team-results.component';
import { MaterialModule } from "../material/material.module";
import { StoreModule } from '@ngrx/store';
import * as fromFantasyTeams from './reducers';
import { EffectsModule } from '@ngrx/effects';
import { FantasyTeamMemberItemEffects } from './entities/fantasy-team-member-item/fantasy-team-member-item.effects';
import { FantasyTeamItemEffects } from './entities/fantasy-team-item/fantasy-team-item.effects';
import { FlexLayoutModule } from "@angular/flex-layout";
import { CoreModule } from "../core/core.module";


@NgModule({
  declarations: [FantasyTeamsComponent, FantasyTeamComponent, FantasyTeamMembersComponent, FantasyTeamResultsComponent],
  imports: [
    CommonModule,
    FantasyTeamsRoutingModule,
    MaterialModule,
    FlexLayoutModule,
    StoreModule.forFeature(fromFantasyTeams.fantasyTeamsFeatureKey, fromFantasyTeams.reducers, {metaReducers: fromFantasyTeams.metaReducers}),
    EffectsModule.forFeature([FantasyTeamMemberItemEffects, FantasyTeamItemEffects]),
    CoreModule,
  ]
})
export class FantasyTeamsModule {
}
