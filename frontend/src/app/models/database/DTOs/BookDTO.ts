import { BookDetails } from '../entites/BookDetails';

export class BookDTO {
	bookDetails: BookDetails;
	comment: string;

	constructor(bookDetails: BookDetails, comment: string) {
		this.bookDetails = bookDetails;
		this.comment = comment;
	}
}