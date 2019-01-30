import { Component, Input, OnInit } from '@angular/core';
import { BookDetails } from '../../../models/database/entites/BookDetails';
import { Book } from '../../../models/database/entites/Book';
import {AuthService} from '../../../services/auth/auth.service';

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

	constructor(private authService: AuthService) {
		this.initData();
	}

  initData() {
    this.authService.getUserData().then( () => {
      this.isAuth = this.authService.isAuthenticated();
      this.role_admin = this.authService.isAdmin();
      this.role_employee = this.authService.isUser();
    });
  }

	ngOnInit() {
		this.bookDetails.books.forEach((book) =>{
			if (book.status.id !== 5) {
				this.booksUnlocked.push(book);
			}
		});
	}
}
