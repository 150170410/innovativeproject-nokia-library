export class BookDTO {
	signature: string;
	bookDetailsId: number;
	comments: string;
	bookStatusId: number;

	constructor(signature: string, bookDetailsId: number, comments: string, bookStatusId: number) {
		this.signature = signature;
		this.bookDetailsId = bookDetailsId;
		this.comments = comments;
		this.bookStatusId = bookStatusId;
	}
}