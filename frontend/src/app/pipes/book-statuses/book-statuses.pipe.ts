import { Pipe, PipeTransform } from '@angular/core';
import { Book } from '../../models/database/entites/Book';

@Pipe({
	name: 'bookStatuses'
})
export class BookStatusesPipe implements PipeTransform {

	transform(books: Book[], status?: any): any {
		if (books.length < 1) {
			return 0;
		}

		let count = 0;
		if (status === 'unlocked') {
			books.forEach((book) => {
				if (book.status.id !== 5) {
					count++;
				}
			});
		} else {
			books.forEach((book) => {
				if (book.status.id === status) {
					count++;
				}
			});
		}
		return count;
	}

}
