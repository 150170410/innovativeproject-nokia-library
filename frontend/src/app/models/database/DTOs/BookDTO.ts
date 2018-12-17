export class BookDTO {
	signature: string;
	bookDetailsId: number;
	comments: string;

	constructor(signature: string, bookDetailsId: number, comments: string) {
		this.signature = signature;
		this.bookDetailsId = bookDetailsId;
		this.comments = comments;
	}
}