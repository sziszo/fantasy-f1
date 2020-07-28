import { AfterViewInit, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { DriverTableDataSource } from './driver-table-datasource';
import { Store } from "@ngrx/store";
import { DriverItem } from "../entities/driver-item/driver-item.model";
import { selectDriverTableItems } from "../entities/driver-item/driver-item.selector";
import { takeUntil } from "rxjs/operators";
import { BehaviorSubject, Subject } from "rxjs";

@Component({
  selector: 'app-driver-table',
  templateUrl: './driver-table.component.html',
  styleUrls: ['./driver-table.component.scss']
})
export class DriverTableComponent implements AfterViewInit, OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<DriverItem>;
  dataSource: DriverTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'constructorName', 'price', 'actions'];

  private unsubscribe$ = new Subject<void>();

  private _constructorIdsChanged = new BehaviorSubject<string[]>([]);

  constructor(private store: Store) {
  }

  ngOnInit() {
    this.dataSource = new DriverTableDataSource(
      this.store.select(selectDriverTableItems)
        .pipe(takeUntil(this.unsubscribe$)),
      this._constructorIdsChanged
        .pipe(takeUntil(this.unsubscribe$))
    );
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  get constructorIds(): string[] {
    return this?.dataSource.constructorIds;
  };

  @Input()
  set constructorIds(constructorIds: string[]) {
    this._constructorIdsChanged.next(constructorIds);
  }

}
