import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupDiscussionPageComponent } from './group-discussion-page.component';

describe('GroupDiscussionPageComponent', () => {
  let component: GroupDiscussionPageComponent;
  let fixture: ComponentFixture<GroupDiscussionPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupDiscussionPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupDiscussionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
