import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { User } from '../../../../models/database/entites/User';
import {MessageInfo} from '../../../../models/MessageInfo';
import {RestService} from '../../../../services/rest/rest.service';
import {Role} from '../../../../models/database/entites/Role';
import {SnackbarService} from '../../../../services/snackbar/snackbar.service';

@Component({
	selector: 'app-manage-users',
	templateUrl: './manage-users.component.html',
	styleUrls: ['./manage-users.component.css', '../../admin-panel.component.scss']
})
export class ManageUsersComponent implements OnInit {

  users: User[] = [];
  isMoreThanOneAdmin: boolean;

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<User>();
	displayedColumns: string[] = [ 'email', 'fullName', 'role', 'address', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
              private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getUsers();
	}

	async getUsers() {
    const response: MessageInfo = await this.http.getAll('user/getAll');
    if (response && response.object) {
      this.users = response.object;
      this.setAdminRoleFirst();
    }
    this.setDataToTable();
  }

	setDataToTable() {
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.filterPredicate = (data, filter: string) => {
        return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
      };
      this.dataSource.sort = this.sort;
  }

	setAdminRoleFirst() {
	  let howMuchAdmin = 0;
	  this.users.forEach( (user) => {
	    user.roles.sort((a: Role, b: Role) => {
        if (a.role < b.role) {
          return -1;
        } else if (a.role > b.role) {
          return 1;
        } else {
          return 0;
        }
      });
	    if (user.roles[0].role === 'ROLE_ADMIN') {
	      howMuchAdmin ++;
      }
    });
	  if (howMuchAdmin > 1) {
	    this.isMoreThanOneAdmin = true;
    } else {
	    this.isMoreThanOneAdmin = false;
    }
  }

	promoteUser(user: User) {
    this.http.save('user/assignAdmin/' + user.id, null).subscribe( (response) => {
      if (response.success) {
        this.getUsers();
        this.snackbar.snackSuccess('User promoted successfully!', 'OK');
      } else {
        this.snackbar.snackError('Error', 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
	}

	demoteUser(user: User) {
    this.http.save('user/takeAdmin/' + user.id, null).subscribe( (response) => {
      if (response.success) {
        this.getUsers();
        this.snackbar.snackSuccess('User demoted successfully!', 'OK');
      } else {
        this.snackbar.snackError('Error', 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
