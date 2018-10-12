import { Routes } from '@angular/router';
import { HomepageComponent} from './components/homepage/homepage.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'homepage', pathMatch: 'full' },
	{ path: 'homepage', component: HomepageComponent },


	// {path: '**', component: NotfoundComponent}

];
