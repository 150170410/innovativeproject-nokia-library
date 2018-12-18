import { BookStatusesPipe } from './book-statuses.pipe';

describe('BookStatusesPipe', () => {
	it('create an instance', () => {
		const pipe = new BookStatusesPipe();
		expect(pipe).toBeTruthy();
	});
});
