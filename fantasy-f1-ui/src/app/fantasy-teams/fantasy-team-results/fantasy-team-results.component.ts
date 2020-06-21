import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { FantasyTeamResultsDataSource, FantasyTeamResultsItem } from './fantasy-team-results-datasource';

@Component({
  selector: 'app-fantasy-team-results',
  templateUrl: './fantasy-team-results.component.html',
  styleUrls: ['./fantasy-team-results.component.scss']
})
export class FantasyTeamResultsComponent implements AfterViewInit, OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<FantasyTeamResultsItem>;
  dataSource: FantasyTeamResultsDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'name'];

  ngOnInit() {
    this.dataSource = new FantasyTeamResultsDataSource();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }
}
