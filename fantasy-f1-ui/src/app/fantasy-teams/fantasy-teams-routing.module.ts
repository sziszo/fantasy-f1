import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FantasyTeamComponent } from "./fantasy-team/fantasy-team.component";
import { DriverTableComponent } from "../core/driver-table/driver-table.component";
import { ConstructorTableComponent } from "../core/constructor-table/constructor-table.component";
import { FantasyTeamResultsComponent } from "./fantasy-team-results/fantasy-team-results.component";


const routes: Routes = [
  {
    path: "my", component: FantasyTeamComponent, children: [
      {
        path: "results", component: FantasyTeamResultsComponent
      },
      {
        path: "drivers", component: DriverTableComponent
      },
      {
        path: "constructors", component: ConstructorTableComponent
      }
    ]
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FantasyTeamsRoutingModule {
}
