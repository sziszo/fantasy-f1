import { Component, Input, OnInit } from '@angular/core';
import { Store } from "@ngrx/store";
import { selectAllSortedTeamMemberItems, } from "../entities/team-member-category-item/team-member-category-item.selector";
import { getSelectedSeasonItem } from "../entities/season-item/season-item.selector";
import { SeasonItem } from "../entities/season-item/season-item.model";
import { setMyFantasyTeamMemberItem } from "../../fantasy-teams/entities/fantasy-team-member-item/fantasy-team-member-item.actions";
import { getTeamMemberImageClass } from "../entities/team-member-category-item/team-member-category-item.model";

export interface TeamMemberAction {
  id: string; //TeamMemberCategoryType
  name: string;
  icon: string;
}

@Component({
  selector: 'app-team-member-action-selector',
  templateUrl: './team-member-action-selector.component.html',
  styleUrls: ['./team-member-action-selector.component.scss']
})
export class TeamMemberActionSelectorComponent implements OnInit {

  teamMemberActions: TeamMemberAction[]

  @Input()
  isConstructor: boolean = false;

  @Input()
  id: string

  private selectedSeasonItem: SeasonItem

  constructor(private store: Store) {
    this.store.select(getSelectedSeasonItem).subscribe(seasonItem => this.selectedSeasonItem = seasonItem)
  }

  ngOnInit(): void {
    this.store.select(selectAllSortedTeamMemberItems)
      .subscribe(items => {
          this.teamMemberActions = items
            .filter(item => item.constructor == this.isConstructor)
            .map(item => {
              return {
                id: item.id,
                name: `Add to ${item.name}`,
                icon: getTeamMemberImageClass(item.id)
              } as TeamMemberAction;
            });
        }
      )
  }

  onSelectionChanged(teamMemberCategoryType: string) {
    if (!teamMemberCategoryType) return;

    if (!this.selectedSeasonItem) {
      console.warn('missing selected season item!!!')
      return;
    }

    console.log(`selected ${this.id} for ${teamMemberCategoryType}`)

    //call setMyFantasyTeamMemberItem action
    this.store.dispatch(setMyFantasyTeamMemberItem({
      series: this.selectedSeasonItem.series,
      season: this.selectedSeasonItem.season,
      id: this.id,
      teamMemberCategoryType: teamMemberCategoryType
    }));

  }

}
