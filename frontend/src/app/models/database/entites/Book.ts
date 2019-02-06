import { BookDetails } from './BookDetails';
import { BookStatus } from './BookStatus';
import { BookOwnerId } from './BookOwnerId';

export class Book {
	id: number;
	signature: string;
	bookDetails: BookDetails;
	comments: string;
	status: BookStatus;
	availableDate: Date;
	currentOwnerId: number;
	ownersId: BookOwnerId[];
}
