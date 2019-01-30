import { Routes } from '@angular/router';
import { HomepageComponent } from './utils/components/homepage/homepage.component';
import { PageNotFoundComponent } from './utils/components/page-not-found/page-not-found.component';
import { SingleBookViewComponent } from './components/views/single-book-view/single-book-view';
import { ContactUsComponent } from './components/contact-us/contact-us.component';
import { UserPanelComponent } from './components/user-panel/user-panel.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from "./components/registration/registration.component";
import { TableViewComponent } from './components/views/table-view/table-view.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'homepage', pathMatch: 'full' },
	{ path: 'homepage', component: HomepageComponent },
	{ path: 'tableView', component: TableViewComponent },
	{ path: 'admin-panel', component: AdminPanelComponent },
	{ path: 'user-panel', component: UserPanelComponent },
	{ path: 'login', component: LoginComponent },
	{ path: 'book/:id', component: SingleBookViewComponent, data: { id: 'id' } },
	{ path: 'contact', component: ContactUsComponent },
	{ path: 'register', component: RegistrationComponent },

	{ path: '**', component: PageNotFoundComponent }
];
