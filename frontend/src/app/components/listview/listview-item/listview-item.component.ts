import { Component, Input, OnInit } from '@angular/core';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import { Book } from '../../../models/database/entites/Book';

@Component({
	selector: 'app-listview-item',
	templateUrl: './listview-item.component.html',
	styleUrls: ['./listview-item.component.css']
})
export class ListviewItemComponent implements OnInit {

	isAuth = false;
	role_admin = false;
	role_employee = false;

	@Input() bookDetails: BookDetails;
	booksUnlocked: Book[] = [];

	constructor() {
		this.isAuth = (sessionStorage.getItem('authenticated') === 'true');
		this.role_admin = (sessionStorage.getItem('ROLE_ADMIN') === 'true');
		this.role_employee = (sessionStorage.getItem('ROLE_EMPLOYEE') === 'true');
	}

	ngOnInit() {
		this.bookDetails.books.forEach((book) =>{
			if(book.status.id !== 5){
				this.booksUnlocked.push(book);
			}
		})
	}

}
