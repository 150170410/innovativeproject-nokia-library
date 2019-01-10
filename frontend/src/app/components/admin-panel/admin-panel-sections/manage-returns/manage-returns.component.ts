import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { Book } from '../../../../models/database/entites/Book';
import { MessageInfo } from '../../../../models/MessageInfo';
import { RestService } from '../../../../services/rest/rest.service';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
	selector: 'app-manage-returns',
	templateUrl: './manage-returns.component.html',
	styleUrls: ['./manage-returns.component.css', '../../admin-panel.component.scss']
})
export class ManageReturnsComponent implements OnInit {


	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Book>();
	displayedColumns: string[] = ['signature', 'current_user', 'status', 'bookDetails', 'comments', 'actions'];

	constructor(private http: RestService) {
	}

	ngOnInit() {
		this.getBookCopies()
	}

	returnBook() {

	}

	async getBookCopies() {
		const response: MessageInfo = await this.http.getAll('books/getAll');
		let borrowedBooks = [];
		for(let i = 0; i < response.object.length; i++){
			if(response.object[i].status.id === 2 || response.object[i].status.id === 3){
				borrowedBooks.push(response.object[i])
			}
		}
		this.dataSource = new MatTableDataSource(borrowedBooks.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
