import { Routes } from '@angular/router';
import { HomepageComponent } from './utils/components/homepage/homepage.component';
import { ListviewComponent } from './components/listview/listview.component';
import { PageNotFoundComponent } from './utils/components/page-not-found/page-not-found.component';
import { GridViewComponent } from './components/grid-view/grid-view.component';
import { SingleBookViewComponent } from './components/single-book-view/single-book-view';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { UserPanelComponent } from './components/user-panel/user-panel.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import {LoginComponent} from './components/auth/login/login.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'homepage', pathMatch: 'full' },
	{ path: 'homepage', component: HomepageComponent },
	{ path: 'listView', component: ListviewComponent },
	{ path: 'gridView', component: GridViewComponent },
	{ path: 'admin-panel', component: AdminPanelComponent },
	{ path: 'user-panel', component: UserPanelComponent },
  { path: 'login', component: LoginComponent},
	{ path: 'single-book-view/:id', component: SingleBookViewComponent, data: { id: 'id' } },
	{ path: 'contact', component: ContactUsComponent },

	{ path: '**', component: PageNotFoundComponent }

];
