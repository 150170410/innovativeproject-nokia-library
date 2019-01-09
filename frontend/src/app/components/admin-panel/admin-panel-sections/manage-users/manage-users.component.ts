import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { User } from '../../../../models/database/entites/User';

@Component({
	selector: 'app-manage-users',
	templateUrl: './manage-users.component.html',
	styleUrls: ['./manage-users.component.css', '../../admin-panel.component.css']
})
export class ManageUsersComponent implements OnInit {

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<User>();
	displayedColumns: string[] = ['id', 'email', 'fullName', 'role', 'address', 'actions'];

	constructor() {
	}

	ngOnInit() {
		this.getUsers();
	}

	getUsers() {
		// const response: MessageInfo = await this.http.getAll('books/getAll');
		this.dataSource = new MatTableDataSource(User.all());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
	}

	promoteUser(user: User) {

	}

	demoteUser(user: User) {

	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
