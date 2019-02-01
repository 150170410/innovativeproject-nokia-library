import { TestBed } from '@angular/core/testing';

import { IsbnValidationService } from './isbn-validation.service';

describe('IsbnValidationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IsbnValidationService = TestBed.get(IsbnValidationService);
    expect(service).toBeTruthy();
  });
});
