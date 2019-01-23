import { BookDetails } from './BookDetails';
import { BookStatus } from './BookStatus';
import { User } from './User';

export class Book {
	id: number;
	signature: string;
	bookDetails: BookDetails;
	comments: string;
	status: BookStatus;
	availableDate: Date;
	currentOwnerId: number;
}
