import { AfterViewInit, Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { ConstructorTableDataSource } from './constructor-table-datasource';
import { Store } from "@ngrx/store";
import { selectConstructorTableItems } from "../entities/constructor-item/constructor-item.selector";
import { ConstructorTableItem } from "../entities/constructor-item/constructor-item.model";
import { BehaviorSubject, Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";

@Component({
  selector: 'app-constructor-table',
  templateUrl: './constructor-table.component.html',
  styleUrls: ['./constructor-table.component.scss']
})
export class ConstructorTableComponent implements AfterViewInit, OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatTable) table: MatTable<ConstructorTableItem>;
  dataSource: ConstructorTableDataSource;

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['name', 'nationality', 'price', 'actions'];

  private unsubscribe$ = new Subject<void>();

  private _constructorIdsChanged = new BehaviorSubject<string[]>([]);

  constructor(private store: Store) {
  }

  ngOnInit() {
    this.dataSource = new ConstructorTableDataSource(
      this.store.select(selectConstructorTableItems)
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
