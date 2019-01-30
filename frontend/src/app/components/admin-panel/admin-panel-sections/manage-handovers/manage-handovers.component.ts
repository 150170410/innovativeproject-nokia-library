import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { MessageInfo } from '../../../../models/MessageInfo';
import { Book } from '../../../../models/database/entites/Book';
import { RestService } from '../../../../services/rest/rest.service';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { Rental } from '../../../../models/database/entites/Rental';
import { BookStatusEnum } from '../../../../utils/BookStatusEnum';

@Component({
	selector: 'app-manage-handovers',
	templateUrl: './manage-handovers.component.html',
	styleUrls: ['./manage-handovers.component.css', '../../admin-panel.component.scss']
})
export class ManageHandoversComponent implements OnInit {

	rentalsAll: Rental[] = [];
	rentals: Rental[] = [];

	// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Rental>();
	displayedColumns: string[] = ['signature', 'current_user', 'owner', 'status', 'bookDetails', 'comments', 'actions'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getRentals();
	}

	handoverBook(rental: Rental) {
		let body = {};
		this.http.save('rentals/handover/' + rental.id, body).subscribe((response) => {
			if (response.success) {
				this.getRentals();
				this.snackbar.snackSuccess(response.message, 'OK');
			} else {
				this.snackbar.snackError('Error', 'OK');
			}
		}, (error) => {
			this.snackbar.snackError(error.error.message, 'OK');
		});
	}



	async getRentals() {
		const response: MessageInfo = await this.http.getAll('rentals/getAllFill');
		this.rentalsAll = response.object;
		this.rentals = [];
		for (let i = 0; i < this.rentalsAll.length; i++) {
			if ((this.rentalsAll[i].book.status.id === BookStatusEnum.AWAITING || this.rentalsAll[i].book.status.id === BookStatusEnum.RESERVED) && this.rentalsAll[i].isCurrent === true) {
				this.rentals.push(this.rentalsAll[i]);
			}
		}
		this.dataSource = new MatTableDataSource(this.rentals.reverse());
		this.dataSource.paginator = this.paginator;
		this.dataSource.filterPredicate = (data, filter: string) => {
			return JSON.stringify(data).toLowerCase().includes(filter.toLowerCase());
		};
		this.dataSource.sort = this.sort;
	}

	applyFilter(filterValue: string) {
		this.dataSource.filter = filterValue.trim().toLowerCase();
	}
}
