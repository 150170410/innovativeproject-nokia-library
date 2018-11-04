import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'app-manage-book',
	templateUrl: './manage-book.component.html',
	styleUrls: ['./manage-book.component.css']
})
export class ManageBookComponent implements OnInit {

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
