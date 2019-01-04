import { Component, OnInit } from '@angular/core';
import { RestService } from '../../../../services/rest/rest.service';

@Component({
	selector: 'app-manage-requests',
	templateUrl: './manage-requests.component.html',
	styleUrls: ['./manage-requests.component.css', '../../admin-panel.component.css']
})
export class ManageRequestsComponent implements OnInit {

	constructor(private http: RestService) {
	}

	ngOnInit() {
	}

}
