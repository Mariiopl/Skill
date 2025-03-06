import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaParticipantesIdComponent } from './lista-participantes-id.component';

describe('ListaParticipantesIdComponent', () => {
  let component: ListaParticipantesIdComponent;
  let fixture: ComponentFixture<ListaParticipantesIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaParticipantesIdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaParticipantesIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
