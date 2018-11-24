import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { routes } from './routes';
import 'hammerjs';
import { AppComponent } from './app.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
	MatAutocompleteModule, MatButtonModule, MatButtonToggleModule, MatCardModule, MatCheckboxModule, MatChipsModule, MatDatepickerModule, MatDialogModule, MatExpansionModule, MatFormFieldModule, MatGridListModule, MatIconModule, MatInputModule, MatListModule, MatMenuModule, MatNativeDateModule,
	MatPaginatorModule, MatProgressBarModule, MatProgressSpinnerModule, MatRadioModule, MatSelectModule, MatSidenavModule, MatSliderModule, MatSlideToggleModule, MatSnackBarModule, MatSortModule, MatStepperModule, MatTableModule, MatTabsModule, MatToolbarModule, MatTooltipModule
} from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ListviewComponent } from './components/listview/listview.component';
import { BookService } from './services/book/book.service';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { RestService } from './services/rest/rest.service';
import { GridViewComponent } from './components/grid-view/grid-view.component';
import { ManageBookDetailsComponent } from './components/manage-entities/forms/manage-book-details/manage-book-details.component';
import { SingleBookViewComponent } from './components/single-book-view/single-book-view';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { ManageEntitiesComponent } from './components/manage-entities/manage-entities.component';
import { ManageAuthorsComponent } from './components/manage-entities/forms/manage-authors/manage-authors.component';
import { ManageCategoriesComponent } from './components/manage-entities/forms/manage-categories/manage-categories.component';
import { SidenavService } from './services/sidenav/sidenav.service';
import { ListviewItemComponent } from './components/listview/listview-item/listview-item.component';
import { ArrToStrPipe } from './pipes/arr-to-str.pipe';
import { UserPanelComponent } from './components/user-panel/user-panel.component';

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
		ManageEntitiesComponent,
		ManageAuthorsComponent,
		ManageCategoriesComponent,
		ListviewItemComponent,
		ArrToStrPipe,
		UserPanelComponent
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
	providers: [BookService, RestService, SidenavService],
	bootstrap: [AppComponent]
})
export class AppModule {
}
