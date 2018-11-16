export class BookDetailsDTO {
	coverPictureUrl: string;
	dateOfPublication: Date;
	description: string;
	isbn: string;
	tableOfContents: string;
	title: string;
	// authors: Array<AuthorDTO>;
	// categories: Array<BookCategoryDTO>;

	constructor(coverPictureUrl: string, dateOfPublication: Date, description: string, isbn: string, tableOfContents: string, title: string) {
	  this.coverPictureUrl = coverPictureUrl;
	  this.dateOfPublication = dateOfPublication;
	  this.description = description;
	  this.isbn = isbn;
	  this.tableOfContents = tableOfContents;
	  this.title = title;
	}
}

