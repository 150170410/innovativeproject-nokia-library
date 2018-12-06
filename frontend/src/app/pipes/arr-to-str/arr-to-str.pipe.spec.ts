import { ArrToStrPipe } from './arr-to-str.pipe';

describe('ArrToStrPipe', () => {
	it('create an instance', () => {
		const pipe = new ArrToStrPipe();
		expect(pipe).toBeTruthy();
	});
});
