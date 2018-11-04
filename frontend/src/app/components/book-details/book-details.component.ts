import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-book-details',
	templateUrl: './book-details.component.html',
	styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

	id: any;

	constructor(private activatedRoute: ActivatedRoute) {
	}

	ngOnInit() {
		this.activatedRoute.params.subscribe((params) => {
			this.id = params['id'];
		});
		console.log(this.id);
	}

}
