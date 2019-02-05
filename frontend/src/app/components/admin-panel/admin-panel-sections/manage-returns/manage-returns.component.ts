import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { MessageInfo } from '../../../../models/MessageInfo';
import { RestService } from '../../../../services/rest/rest.service';
import { Rental } from '../../../../models/database/entites/Rental';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { BookStatusEnum } from '../../../../utils/BookStatusEnum';

@Component({
	selector: 'app-manage-returns',
	templateUrl: './manage-returns.component.html',
	styleUrls: ['./manage-returns.component.scss', '../../admin-panel.component.scss']
})
export class ManageReturnsComponent implements OnInit {

	rentalsAll: Rental[] = [];
	rentals: Rental[] = [];
	isLoadingResults = true;

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Rental>();
	displayedColumns: string[] = ['signature', 'current_user', 'owner', 'bookDetails', 'comments', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getRentals();
	}

	returnBook(rental: Rental) {
		const body = {};
		this.http.save('rentals/return/' + rental.id, body).subscribe((response) => {
			if (response.success) {
				this.getRentals();
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
			this.getRentals();
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}

	async getRentals() {
		this.isLoadingResults = true;
		const response: MessageInfo = await this.http.getAll('rentals/getAllFill');
		this.rentalsAll = response.object;
		this.rentals = [];
		for (let i = 0; i < this.rentalsAll.length; i++) {
			if (this.rentalsAll[i].book.status.id == BookStatusEnum.BORROWED && this.rentalsAll[i].isCurrent === true) {
				this.rentals.push(this.rentalsAll[i]);
			}
		}
		this.dataSource = new MatTableDataSource(this.rentals.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
		this.isLoadingResults = false;
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
