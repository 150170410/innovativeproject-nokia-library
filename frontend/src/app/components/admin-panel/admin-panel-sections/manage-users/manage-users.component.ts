import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { User } from '../../../../models/database/entites/User';
import { MessageInfo } from '../../../../models/MessageInfo';
import { RestService } from '../../../../services/rest/rest.service';
import { Role } from '../../../../models/database/entites/Role';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Address } from '../../../../models/database/entites/Address';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { UserDTO } from '../../../../models/database/DTOs/UserDTO';
import {AuthService} from '../../../../services/auth/auth.service';

@Component({
	selector: 'app-manage-users',
	templateUrl: './manage-users.component.html',
	styleUrls: ['./manage-users.component.scss', '../../admin-panel.component.scss']
})
export class ManageUsersComponent implements OnInit {

	users: User[] = [];
	isMoreThanOneAdmin: boolean;
	userParams: FormGroup;
	actualUser: User;

	addresses: Address[] = [];
	availableCities: string[] = [];
	availableBuildings: string[] = [];

	filteredBuilding: Observable<string[]>;
	filteredCities: Observable<string[]>;
	myControl = new FormControl();
	my2Control = new FormControl();

	updatedBuilding: string;
	updatedCity: string;

	loggedUserName: string;

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<User>();
	displayedColumns: string[] = ['email', 'fullName', 'role', 'address', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
				private snackbar: SnackbarService,
				private formBuilder: FormBuilder,
              private authService: AuthService) {
	}

	ngOnInit() {
		this.getUsers();
		this.initFormGroup();
		this.getAddresses();
    this.initData();
	}

  initData() {
    this.authService.getUserData().then( () => {
      this.loggedUserName = this.authService.getUsername();
    });
  }

	getAddresses() {
		this.initAddresses().then(() => {
			this.filteredBuilding = this.myControl.valueChanges
			.pipe(
				startWith(''),
				map(value => this._filter(value))
			);
			this.filteredCities = this.my2Control.valueChanges
			.pipe(
				startWith(''),
				map(value => this._filter2(value))
			);
		});
	}

	private _filter(value: string): string[] {
		const filterValue = value.toLowerCase();
		this.updatedBuilding = value;
		return this.availableBuildings.filter(option => option.toLowerCase().includes(filterValue));
	}

	private _filter2(value: string): string[] {
		const filterValue = value.toLowerCase();
		this.updatedCity = value;
		return this.availableCities.filter(option => option.toLowerCase().includes(filterValue));
	}

	async initAddresses() {
		const response: MessageInfo = await this.http.getAll('address/getAll');
		this.addresses = [];
		if (response && response.object) {
			this.addresses = response.object;
		}
		this.addresses.forEach((address) => {
			this.availableBuildings.push(address.building);
			this.availableCities.push(address.city);
		});
	}

	initFormGroup() {
		this.userParams = this.formBuilder.group({
			name: ['', [Validators.required]],
			surname: ['', [Validators.required]],
			addressCity: ['', [Validators.required]],
			email: ['', [Validators.required, Validators.email]],
			addressBuilding: ['', [Validators.required]]
		});
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
		this.users.forEach((user) => {
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
				howMuchAdmin++;
			}
		});
		if (howMuchAdmin > 1) {
			this.isMoreThanOneAdmin = true;
		} else {
			this.isMoreThanOneAdmin = false;
		}
	}

	promoteUser(user: User) {
		this.http.save('user/assignAdmin/' + user.id, null).subscribe((response) => {
			if (response.success) {
				this.getUsers();
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError(response.message, 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	demoteUser(user: User) {
		this.http.save('user/takeAdmin/' + user.id, null).subscribe((response) => {
			if (response.success) {
				this.getUsers();
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError(response.message, 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

  unlockAccount(user: User) {
    this.http.save('user/unlockAccount/' + user.id, null).subscribe((response) => {
      if (response.success) {
        this.getUsers();
        this.snackbar.snackSuccess(response.message, 'OK');
      } else {
        this.snackbar.snackError(response.message, 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
  }


  lockAccount(user: User) {
    this.http.save('user/lockAccount/' + user.id, null).subscribe((response) => {
      if (response.success) {
        this.getUsers();
        this.snackbar.snackSuccess(response.message, 'OK');
      } else {
        this.snackbar.snackError(response.message, 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
  }

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}

	fillUserTable(user: User) {
		this.actualUser = user;
		this.userParams.patchValue({
			name: user.firstName,
			surname: user.lastName,
			addressCity: user.address.city,
			email: user.email,
			addressBuilding: user.address.building
		});
		document.getElementById('admin-panel-tabs').scrollIntoView();
	}

	changeUserButtonClick(userParams) {
		if (!this.actualUser) {
			this.snackbar.snackError('Please select a user to change data!', 'OK');
		} else {
			const name: string = this.userParams.value.name;
			const surname: string = this.userParams.value.surname;
			let addressCity: string = this.userParams.value.addressCity;
			const email: string = this.userParams.value.email;
			let addressBuilding: string = this.userParams.value.addressBuilding;

			if (this.updatedCity) {
				addressCity = this.updatedCity;
			}
			if (this.updatedBuilding) {
				addressBuilding = this.updatedBuilding;
			}

			let address: Address = this.addresses.filter((add) =>
				add.city === addressCity && add.building === addressBuilding)[0];
			if (!address) {
				address = new Address(null, addressCity, addressBuilding);
			}

			const user = new UserDTO(name, surname, 'passwordIsNotRequiredInThisExample', email, address);

			console.log(user);

			this.http.update('user', this.actualUser.id, user).subscribe((response) => {
				if (response.success) {
					this.getUsers();
					this.getAddresses();
					this.snackbar.snackSuccess(response.message, 'OK');
					this.userParams.reset();
					this.actualUser = null;
				} else {
					this.snackbar.snackError('Error', 'OK');
				}
			}, (error) => {
				this.snackbar.snackError(error.error.message, 'OK');
			});
		}
	}

  shareBooks(user: any) {
    this.http.save('books/assignOwnerToAll/' + user.id, null).subscribe((response) => {
      if (response.success) {
        this.getUsers();
        this.snackbar.snackSuccess(response.message, 'OK');
      } else {
        this.snackbar.snackError(response.message, 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
  }

  transferBooks(user: any) {
    this.http.save('books/transferToAdmin/' + user.id, null).subscribe((response) => {
      if (response.success) {
        this.getUsers();
        this.snackbar.snackSuccess(response.message, 'OK');
      } else {
        this.snackbar.snackError(response.message, 'OK');
      }
    }, (error) => {
      this.snackbar.snackError(error.error.message, 'OK');
    });
  }
}
