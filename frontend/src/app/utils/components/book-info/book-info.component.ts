import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
	selector: 'app-book-info',
	templateUrl: './book-info.component.html',
	styleUrls: ['./book-info.component.css']
})
export class BookInfoComponent implements OnInit {

	@Input() item: any;

	constructor(private router: Router) {
	}

	ngOnInit() {
	}

	bookInfo() {
		this.router.navigateByUrl( '/book/' + this.item.book.bookDetails.id);
	}

}
