import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ConfirmationDialogComponent } from '../../utils/components/confirmation-dialog/confirmation-dialog.component';

@Injectable({
	providedIn: 'root'
})
export class ConfirmationDialogService {

	constructor(public dialog: MatDialog) {
	}

	openDialog() {
		const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
			width: '250px'
		});
		return dialogRef.afterClosed();
	}
}
