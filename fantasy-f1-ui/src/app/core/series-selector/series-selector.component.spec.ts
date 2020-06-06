import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeriesSelectorComponent } from './series-selector.component';

describe('SeasonSelectorComponent', () => {
  let component: SeriesSelectorComponent;
  let fixture: ComponentFixture<SeriesSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeriesSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
