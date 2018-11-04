import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { ListviewComponent } from './components/listview/listview.component';
import { BookDetailsComponent } from './components/book-details/book-details.component';
import { ManageBookComponent } from './components/manage-book/manage-book.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'homepage', pathMatch: 'full' },
	{ path: 'homepage', component: HomepageComponent },
	{ path: 'listview', component: ListviewComponent },
	{ path: 'book-details/:id', component: BookDetailsComponent, data: { id: 'id' } },
	{ path: 'manage-book/:id', component: ManageBookComponent, data: { id: 'id' } },
	{ path: '**', component: PageNotFoundComponent }

];
