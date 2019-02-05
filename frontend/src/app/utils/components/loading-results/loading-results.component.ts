import { Component, Input, OnInit } from '@angular/core';

@Component({
	selector: 'app-loading-results',
	templateUrl: './loading-results.component.html',
	styleUrls: ['./loading-results.component.scss']
})
export class LoadingResultsComponent implements OnInit {

	@Input() isLoadingResults: boolean;

	constructor() {
	}

	ngOnInit() {
	}

}
