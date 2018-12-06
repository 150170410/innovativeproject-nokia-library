import { Component, Input, OnInit } from '@angular/core';
import { BookDetails } from '../../../models/database/entites/BookDetails';

@Component({
	selector: 'app-listview-item',
	templateUrl: './listview-item.component.html',
	styleUrls: ['./listview-item.component.css']
})
export class ListviewItemComponent implements OnInit {

	@Input() bookDetails: BookDetails;

	constructor() {
	}

	ngOnInit() {
	}

}
