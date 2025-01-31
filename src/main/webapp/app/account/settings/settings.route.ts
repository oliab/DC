import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { SettingsComponent } from './settings.component';
import { Authority } from 'app/shared/constants/authority.constants';

export const settingsRoute: Route = {
  path: 'settings',
  component: SettingsComponent,
  data: {
    authorities: [Authority.SUPERVISOR],
    pageTitle: 'global.menu.account.settings',
  },
  canActivate: [UserRouteAccessService],
};
