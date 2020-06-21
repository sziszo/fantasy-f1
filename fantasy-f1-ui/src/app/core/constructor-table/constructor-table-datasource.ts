import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { filter, map } from 'rxjs/operators';
import { BehaviorSubject, merge, Observable } from 'rxjs';
import { ConstructorTableItem } from "../entities/constructor-item/constructor-item.model";


/**
 * Data source for the ConstructorTable view. This class should
 * encapsulate all logic for fetching and manipulating the displayed data
 * (including sorting, pagination, and filtering).
 */
export class ConstructorTableDataSource extends DataSource<ConstructorTableItem> {
  data: ConstructorTableItem[] = [];
  paginator: MatPaginator;
  sort: MatSort;

  dataChanged = new BehaviorSubject<boolean>(false)
  private _constructorIds: string[] = [];

  constructor(constructorItems$: Observable<ConstructorTableItem[]>, constructorIdsChanged$: Observable<string[]>) {
    super();

    constructorItems$.subscribe(value => {
      if (value.length == 0 && value.length == this.data.length) {
        return;
      }

      this.data = value;
      this.dataChanged.next(true);
      this.refresh()
    });

    constructorIdsChanged$.subscribe(value => {
      this._constructorIds = value;
      this.refresh();
    })
  }

  refresh() {
    this.data.forEach(value => value.enabled = !this._constructorIds.includes(value.id))
  }

  get constructorIds(): string[] {
    return this._constructorIds;
  }

  /**
   * Connect this data source to the table. The table will only update when
   * the returned stream emits new items.
   * @returns A stream of the items to be rendered.
   */
  connect(): Observable<ConstructorTableItem[]> {
    // Combine everything that affects the rendered data into one update
    // stream for the data-table to consume.
    const dataMutations = [
      this.dataChanged.asObservable(),
      this.paginator.page,
      this.sort.sortChange
    ];

    return merge(...dataMutations)
      .pipe(
        filter(value => typeof value === 'boolean' ?? true),
        map(() => this.getPagedData(this.getSortedData([...this.data])))
      );
  }

  /**
   *  Called when the table is being destroyed. Use this function, to clean up
   * any open connections or free any held resources that were set up during connect.
   */
  disconnect() {
    this.dataChanged.complete();
    // console.info('constructor table is disconnected')
  }

  /**
   * Paginate the data (client-side). If you're using server-side pagination,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getPagedData(data: ConstructorTableItem[]) {
    const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
    return data.splice(startIndex, this.paginator.pageSize);
  }

  /**
   * Sort the data (client-side). If you're using server-side sorting,
   * this would be replaced by requesting the appropriate data from the server.
   */
  private getSortedData(data: ConstructorTableItem[]) {
    if (!this.sort.active || this.sort.direction === '') {
      return data;
    }

    return data.sort((a, b) => {
      const isAsc = this.sort.direction === 'asc';
      switch (this.sort.active) {
        case 'name':
          return compare(a.name, b.name, isAsc);
        case 'id':
          return compare(+a.id, +b.id, isAsc);
        default:
          return 0;
      }
    });
  }
}

/** Simple sort comparator for example ID/Name columns (for client-side sorting). */
function compare(a: string | number, b: string | number, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
