import { Pipe, PipeTransform } from '@angular/core';
import { Book } from '../../models/database/entites/Book';

@Pipe({
	name: 'bookStatuses'
})
export class BookStatusesPipe implements PipeTransform {

	transform(books: Book[], status?: any): any {
		if (books.length < 1) {
			return null;
		}

		let count = 0;
		books.forEach((book) =>{
			if(book.status.id === status){
				count++;
			}
		});
		return count;
	}

}
