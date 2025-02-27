import { TestBed } from '@angular/core/testing';

import { GanadoresService } from './ganadores.service';

describe('GanadoresService', () => {
  let service: GanadoresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GanadoresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
