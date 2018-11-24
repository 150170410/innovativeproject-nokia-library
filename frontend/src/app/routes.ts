import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { ListviewComponent } from './components/listview/listview.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { GridViewComponent } from './components/grid-view/grid-view.component';
import { SingleBookViewComponent } from './components/single-book-view/single-book-view';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { ManageEntitiesComponent } from './components/manage-entities/manage-entities.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'homepage', pathMatch: 'full' },
	{ path: 'homepage', component: HomepageComponent },
	{ path: 'listView', component: ListviewComponent },
	{ path: 'gridView', component: GridViewComponent },
	{ path: 'manage-entities', component: ManageEntitiesComponent },
	{ path: 'single-book-view/:id', component: SingleBookViewComponent, data: { id: 'id' } },
	{ path: 'contact', component: ContactUsComponent },

	{ path: '**', component: PageNotFoundComponent }

];
