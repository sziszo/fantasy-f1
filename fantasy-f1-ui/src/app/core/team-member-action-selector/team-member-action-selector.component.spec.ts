import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamMemberActionSelectorComponent } from './team-member-action-selector.component';

describe('TeamMemberActionSelectorComponent', () => {
  let component: TeamMemberActionSelectorComponent;
  let fixture: ComponentFixture<TeamMemberActionSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeamMemberActionSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamMemberActionSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
