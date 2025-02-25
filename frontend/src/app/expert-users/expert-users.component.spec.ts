import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertUsersComponent } from './expert-users.component';

describe('ExpertUsersComponent', () => {
  let component: ExpertUsersComponent;
  let fixture: ComponentFixture<ExpertUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertUsersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
