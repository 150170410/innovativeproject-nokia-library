export class BookCategory {
	id: number;
	bookCategoryName: string;
	isRemovable: boolean;

	constructor(id: number, bookCategoryName: string) {
		this.id = id;
		this.bookCategoryName = bookCategoryName;
	}
}
