import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { User } from '../../../../models/database/entites/User';
import {MessageInfo} from '../../../../models/MessageInfo';
import {RestService} from '../../../../services/rest/rest.service';

@Component({
	selector: 'app-manage-users',
	templateUrl: './manage-users.component.html',
	styleUrls: ['./manage-users.component.css', '../../admin-panel.component.scss']
})
export class ManageUsersComponent implements OnInit {

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<User>();
	displayedColumns: string[] = [ 'email', 'fullName', 'role', 'address', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService) {
	}

	ngOnInit() {
		this.getUsers();
	}

	async getUsers() {
		const response: MessageInfo = await this.http.getAll('user/getAll');

    console.log(response);

		this.dataSource = new MatTableDataSource(User.all());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
	}

	promoteUser(user: User) {

	}

	demoteUser(user: User) {

	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
