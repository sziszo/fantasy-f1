import { Component, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { FantasyTeamMemberItemExt } from "../entities/fantasy-team-member-item/fantasy-team-member-item.model";
import { Store } from "@ngrx/store";
import { selectMyFantasyTeamItems } from "../entities/fantasy-team-member-item/fantasy-team-member-item.selector";
import { getSelectedRaceItem } from "../../core/entities/race-item/race-item.selector";
import {
  deleteMyFantasyTeamMemberItem,
  searchMyFantasyTeamMemberItems
} from "../entities/fantasy-team-member-item/fantasy-team-member-item.actions";
import {
  getTeamMemberImageClass,
  TeamMemberCategoryType
} from "../../core/entities/team-member-category-item/team-member-category-item.model";
import { getSelectedSeasonItem } from "../../core/entities/season-item/season-item.selector";
import { SeasonItem } from "../../core/entities/season-item/season-item.model";

@Component({
  selector: 'app-fantasy-team-members',
  templateUrl: './fantasy-team-members.component.html',
  styleUrls: ['./fantasy-team-members.component.scss']
})
export class FantasyTeamMembersComponent implements OnInit {

  teamMembers$: Observable<FantasyTeamMemberItemExt[]>

  private selectedSeasonItem: SeasonItem

  constructor(private store: Store) {
    this.store.select(getSelectedSeasonItem).subscribe(seasonItem => this.selectedSeasonItem = seasonItem)
    this.teamMembers$ = this.store.select(selectMyFantasyTeamItems)
  }

  ngOnInit(): void {
    this.store
      .select(getSelectedRaceItem)
      .subscribe(value => {
        if (!value) {
          return null;
        }
        this.store.dispatch(searchMyFantasyTeamMemberItems({
          series: value.series, season: value.season, race: value.round
        }));
      })
  }

  isConstructor(teamMemberTypeId: string): boolean {
    let teamMemberTypeElement = TeamMemberCategoryType[teamMemberTypeId];
    return teamMemberTypeElement != TeamMemberCategoryType.DRIVER_1 && teamMemberTypeElement != TeamMemberCategoryType.DRIVER_2;
  }

  getImageClass(teamMemberCategoryType: string) {
    return getTeamMemberImageClass(teamMemberCategoryType);
  }

  getTeamMemberSelectorUrl(teamMemberTypeId: string) {
    switch (TeamMemberCategoryType[teamMemberTypeId]) {
      case TeamMemberCategoryType.DRIVER_1:
      case TeamMemberCategoryType.DRIVER_2:
        return "drivers";
      default:
        return "constructors";
    }
  }

  deleteTeamMember(teamMemberCategoryType: string) {
    if (!teamMemberCategoryType) {
      console.error('missing selected team member category!!!')
      return;
    }

    if (!this.selectedSeasonItem) {
      console.error('missing selected season item!!!')
      return;
    }

    console.log(`selected ${teamMemberCategoryType} to delete`)

    //call setMyFantasyTeamMemberItem action
    this.store.dispatch(deleteMyFantasyTeamMemberItem({
      series: this.selectedSeasonItem.series,
      season: this.selectedSeasonItem.season,
      teamMemberCategoryType: teamMemberCategoryType
    }));
  }


}
