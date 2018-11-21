import { Author } from '../entites/Author';
import { BookCategory } from '../entites/BookCategory';

export class BookDetailsDTO {
	coverPictureUrl: string;
	dateOfPublication: Date;
	description: string;
	isbn: string;
	tableOfContents: string;
	title: string;
	authors: Array<Author>;
	categories: Array<BookCategory>;


	constructor(coverPictureUrl: string, dateOfPublication: Date, description: string, isbn: string, tableOfContents: string, title: string, authors: Array<Author>, categories: Array<BookCategory>) {
		this.coverPictureUrl = coverPictureUrl;
		this.dateOfPublication = dateOfPublication;
		this.description = description;
		this.isbn = isbn;
		this.tableOfContents = tableOfContents;
		this.title = title;
		this.authors = authors;
		this.categories = categories;
	}
}

