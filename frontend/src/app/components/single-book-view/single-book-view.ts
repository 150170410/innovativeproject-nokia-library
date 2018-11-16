import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-book-details',
	templateUrl: './single-book-view.html',
	styleUrls: ['./single-book-view.css']
})
export class SingleBookViewComponent implements OnInit {

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