export class ReservationDTO {
	bookId: number;
	userId: number;

	constructor(bookId: number, userId: number) {
		this.bookId = bookId;
		this.userId = userId;
	}
}