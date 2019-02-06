import { Component, OnInit, ViewChild } from '@angular/core';
import { Rental } from '../../../../models/database/entites/Rental';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { RestService } from '../../../../services/rest/rest.service';
import { SnackbarService } from '../../../../services/snackbar/snackbar.service';
import { MessageInfo } from '../../../../models/MessageInfo';
@Component({
  selector: 'app-manage-history',
  templateUrl: './manage-history.component.html',
  styleUrls: ['./manage-history.component.css']
})
export class ManageHistoryComponent implements OnInit {

  rentals: Rental[] = [];

// table
	@ViewChild('paginator') paginator: MatPaginator;
	dataSource = new MatTableDataSource<Rental>();
	displayedColumns: string[] = ['signature', 'user','rentalDate', 'handOverDate', 'returnDate', 'bookDetails', 'comments'];
	@ViewChild(MatSort) sort: MatSort;

	constructor(private http: RestService,
				private snackbar: SnackbarService) {
	}

	ngOnInit() {
		this.getRentals();
  }
  
  async getRentals() {
		const response: MessageInfo = await this.http.getAll('rentals/history/getAll');
    this.rentals = response.object;
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