import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatRiesgo, CatRiesgo } from 'app/shared/model/cat-riesgo.model';
import { CatRiesgoService } from './cat-riesgo.service';
import { CatRiesgoComponent } from './cat-riesgo.component';
import { CatRiesgoDetailComponent } from './cat-riesgo-detail.component';
import { CatRiesgoUpdateComponent } from './cat-riesgo-update.component';

@Injectable({ providedIn: 'root' })
export class CatRiesgoResolve implements Resolve<ICatRiesgo> {
  constructor(private service: CatRiesgoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatRiesgo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catRiesgo: HttpResponse<CatRiesgo>) => {
          if (catRiesgo.body) {
            return of(catRiesgo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatRiesgo());
  }
}

export const catRiesgoRoute: Routes = [
  {
    path: '',
    component: CatRiesgoComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catRiesgo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatRiesgoDetailComponent,
    resolve: {
      catRiesgo: CatRiesgoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catRiesgo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatRiesgoUpdateComponent,
    resolve: {
      catRiesgo: CatRiesgoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catRiesgo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatRiesgoUpdateComponent,
    resolve: {
      catRiesgo: CatRiesgoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catRiesgo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
