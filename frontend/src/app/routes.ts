import { Routes } from '@angular/router';
import { HomepageComponent} from './components/homepage/homepage.component';
import { ListviewComponent } from './components/listview/listview.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'homepage', pathMatch: 'full' },
	{ path: 'homepage', component: HomepageComponent },
	{ path: 'listview', component: ListviewComponent },


	// {path: '**', component: NotfoundComponent}

];
