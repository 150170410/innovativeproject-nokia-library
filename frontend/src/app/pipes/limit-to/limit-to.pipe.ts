import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
	name: 'limitTo'
})
export class LimitToPipe implements PipeTransform {

	transform(value: string, size?: number): string {
		if (value == null) return value;

		if (value.length >= size) {
			return value.slice(0, size) + '...';
		} else {
			return value;
		}
	}
}
