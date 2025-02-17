import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { guardExpertoGuard } from './guard-experto.guard';

describe('guardExpertoGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => guardExpertoGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
