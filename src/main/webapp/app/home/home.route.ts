import { Route } from '@angular/router';
import { IndexComponent } from '../index/index.component';
import { HomeComponent } from './home.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from 'app/shared/constants/authority.constants';

export const HOME_ROUTE: Route[] = [
  {
    path: '',
    component: IndexComponent,
    data: {
      authorities: [],
      pageTitle: 'home.title',
    },
  },
  {
    path: 'panel',
    component: HomeComponent,
    canActivate: [UserRouteAccessService],
    data: {
      authorities: [Authority.ADMIN, Authority.SUPERVISOR],
      pageTitle: 'home.title',
    },
  },
];
