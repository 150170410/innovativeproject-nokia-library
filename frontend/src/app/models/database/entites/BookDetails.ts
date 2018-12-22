import { Book } from './Book';
import { Author } from './Author';
import { BookCategory } from './BookCategory';

export class BookDetails {
	id: number;
	isbn: string;
	title: string;
	authors: Author [];
	books: Book[];
	categories: BookCategory [];
	coverPictureUrl: string;
	publicationDate: Date;
	description: string;
}
