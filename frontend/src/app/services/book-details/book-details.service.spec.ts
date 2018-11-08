import { TestBed } from '@angular/core/testing';

import { BookDetailsService } from './book-details.service';

describe('BookDetailsService', () => {
	beforeEach(() => TestBed.configureTestingModule({}));

	it('should be created', () => {
		const service: BookDetailsService = TestBed.get(BookDetailsService);
		expect(service).toBeTruthy();
	});
});
