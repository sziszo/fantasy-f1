import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FantasyTeamMembersComponent } from './fantasy-team-members.component';

describe('FantasyTeamMembersComponent', () => {
  let component: FantasyTeamMembersComponent;
  let fixture: ComponentFixture<FantasyTeamMembersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FantasyTeamMembersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FantasyTeamMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
