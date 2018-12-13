import { BookDetails } from '../entites/BookDetails';

export class BookDTO {
	signature: string;
	bookDetails: BookDetails;
	comment: string;

	constructor(signature: string, bookDetails: BookDetails, comment: string) {
		this.signature = signature;
		this.bookDetails = bookDetails;
		this.comment = comment;
	}
}