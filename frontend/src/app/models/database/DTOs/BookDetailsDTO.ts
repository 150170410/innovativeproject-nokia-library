import { Author } from '../entites/Author';
import { BookCategory } from '../entites/BookCategory';

export class BookDetailsDTO {
	isbn: string;
	title: string;
	authors: Array<Author>;
	categories: Array<BookCategory>;
	publicationDate: Date;
	description: string;
	tableOfContents: string;
	coverPictureUrl: string;

	constructor(isbn: string, title: string, authors: Array<Author>, categories: Array<BookCategory>, dateOfPublication: Date, description: string, tableOfContents: string, coverPictureUrl: string) {
		this.isbn = isbn;
		this.title = title;
		this.authors = authors;
		this.categories = categories;
		this.publicationDate = dateOfPublication;
		this.description = description;
		this.tableOfContents = tableOfContents;
		this.coverPictureUrl = coverPictureUrl;
	}
}

