import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { routes } from './routes';
import 'hammerjs';
import { AppComponent } from './app.component';
import { HomepageComponent } from './utils/components/homepage/homepage.component';
import { NavbarComponent } from './utils/components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
	MAT_SNACK_BAR_DEFAULT_OPTIONS, MatAutocompleteModule, MatButtonModule, MatButtonToggleModule, MatCardModule, MatCheckboxModule, MatChipsModule, MatDatepickerModule, MatDialogModule, MatExpansionModule, MatFormFieldModule, MatGridListModule, MatIconModule, MatInputModule, MatListModule,
	MatMenuModule, MatNativeDateModule, MatPaginatorModule, MatProgressBarModule, MatProgressSpinnerModule, MatRadioModule, MatSelectModule, MatSidenavModule, MatSliderModule, MatSlideToggleModule, MatSnackBarModule, MatSortModule, MatStepperModule, MatTableModule, MatTabsModule, MatToolbarModule,
	MatTooltipModule
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ListviewComponent } from './components/listview/listview.component';
import { BookService } from './services/book/book.service';
import { PageNotFoundComponent } from './utils/components/page-not-found/page-not-found.component';
import { RestService } from './services/rest/rest.service';
import { GridViewComponent } from './components/grid-view/grid-view.component';
import { SingleBookViewComponent } from './components/single-book-view/single-book-view';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { ListviewItemComponent } from './components/listview/listview-item/listview-item.component';
import { ArrToStrPipe } from './pipes/arr-to-str/arr-to-str.pipe';
import { UserPanelComponent } from './components/user-panel/user-panel.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { ManageBookDetailsComponent } from './components/admin-panel/admin-panel-sections/manage-book-details/manage-book-details.component';
import { ManageAuthorsComponent } from './components/admin-panel/admin-panel-sections/manage-authors/manage-authors.component';
import { ManageCategoriesComponent } from './components/admin-panel/admin-panel-sections/manage-categories/manage-categories.component';
import { ManageBooksComponent } from './components/admin-panel/admin-panel-sections/manage-books/manage-books.component';
import { LimitToPipe } from './pipes/limit-to/limit-to.pipe';
import { BookStatusesPipe } from './pipes/book-statuses/book-statuses.pipe';
import { ConfirmationDialogComponent } from './utils/components/confirmation-dialog/confirmation-dialog.component';
import { BooksHistoryComponent } from './components/user-panel/user-panel-sections/books-history/books-history.component';
import { BooksBorrowedComponent } from './components/user-panel/user-panel-sections/books-borrowed/books-borrowed.component';
import { BooksReservedComponent } from './components/user-panel/user-panel-sections/books-reserved/books-reserved.component';
import { BooksRequestedComponent } from './components/user-panel/user-panel-sections/books-requested/books-requested.component';
import { ManageReturnsComponent } from './components/admin-panel/admin-panel-sections/manage-returns/manage-returns.component';
import { ManageRequestsComponent } from './components/admin-panel/admin-panel-sections/manage-requests/manage-requests.component';
import { ManageUsersComponent } from './components/admin-panel/admin-panel-sections/manage-users/manage-users.component';
import { BookActionsComponent } from './components/book-actions/book-actions.component';
import { ManageHandoversComponent } from './components/admin-panel/admin-panel-sections/manage-handovers/manage-handovers.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SnackbarService } from './services/snackbar/snackbar.service';
import { TableViewComponent } from './components/table-view/table-view.component';

@NgModule({
	declarations: [
		AppComponent,
		HomepageComponent,
		NavbarComponent,
		ListviewComponent,
		SingleBookViewComponent,
		PageNotFoundComponent,
		GridViewComponent,
		ManageBookDetailsComponent,
		ContactUsComponent,
		ManageAuthorsComponent,
		ManageCategoriesComponent,
		ListviewItemComponent,
		ArrToStrPipe,
		UserPanelComponent,
		ManageBooksComponent,
		AdminPanelComponent,
		LimitToPipe,
		BookStatusesPipe,
		ConfirmationDialogComponent,
		BooksHistoryComponent,
		BooksBorrowedComponent,
		BooksReservedComponent,
		BooksRequestedComponent,
		ManageReturnsComponent,
		ManageRequestsComponent,
		ManageUsersComponent,
		BookActionsComponent,
		ManageHandoversComponent,
		LoginComponent,
		RegistrationComponent,
		TableViewComponent
	],
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		ReactiveFormsModule,
		FormsModule,
		RouterModule.forRoot(routes),
		HttpClientModule,

		MatCheckboxModule,
		MatCheckboxModule,
		MatButtonModule,
		MatInputModule,
		MatAutocompleteModule,
		MatDatepickerModule,
		MatFormFieldModule,
		MatRadioModule,
		MatSelectModule,
		MatSliderModule,
		MatSlideToggleModule,
		MatMenuModule,
		MatSidenavModule,
		MatToolbarModule,
		MatListModule,
		MatGridListModule,
		MatCardModule,
		MatStepperModule,
		MatTabsModule,
		MatExpansionModule,
		MatButtonToggleModule,
		MatChipsModule,
		MatIconModule,
		MatProgressSpinnerModule,
		MatProgressBarModule,
		MatDialogModule,
		MatTooltipModule,
		MatSnackBarModule,
		MatTableModule,
		MatSortModule,
		MatPaginatorModule,
		MatNativeDateModule
	], exports: [
		BrowserAnimationsModule,
		ReactiveFormsModule,
		FormsModule,

		MatCheckboxModule,
		MatCheckboxModule,
		MatButtonModule,
		MatInputModule,
		MatAutocompleteModule,
		MatDatepickerModule,
		MatFormFieldModule,
		MatRadioModule,
		MatSelectModule,
		MatSliderModule,
		MatSlideToggleModule,
		MatMenuModule,
		MatSidenavModule,
		MatToolbarModule,
		MatListModule,
		MatGridListModule,
		MatCardModule,
		MatStepperModule,
		MatTabsModule,
		MatExpansionModule,
		MatButtonToggleModule,
		MatChipsModule,
		MatIconModule,
		MatProgressSpinnerModule,
		MatProgressBarModule,
		MatDialogModule,
		MatTooltipModule,
		MatSnackBarModule,
		MatTableModule,
		MatSortModule,
		MatPaginatorModule,
		MatNativeDateModule
	],
	providers: [BookService, RestService, SnackbarService,
		{ provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: { duration: 3000 } }],
	bootstrap: [AppComponent],
	entryComponents: [ConfirmationDialogComponent]
})
export class AppModule {
}
