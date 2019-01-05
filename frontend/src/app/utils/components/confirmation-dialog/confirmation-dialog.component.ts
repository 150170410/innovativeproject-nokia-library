import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';

@Component({
	selector: 'app-confirmation-dialog',
	templateUrl: './confirmation-dialog.component.html',
	styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent implements OnInit {

	constructor(public dialogRef: MatDialogRef<ConfirmationDialogComponent>) {
	}

	ngOnInit() {
	}

	onNoClick(): void {
		this.dialogRef.close(false);
	}

	toRemove() {
		this.dialogRef.close(true);
	}
}
