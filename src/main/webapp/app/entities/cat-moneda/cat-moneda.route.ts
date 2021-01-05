import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatMoneda, CatMoneda } from 'app/shared/model/cat-moneda.model';
import { CatMonedaService } from './cat-moneda.service';
import { CatMonedaComponent } from './cat-moneda.component';
import { CatMonedaDetailComponent } from './cat-moneda-detail.component';
import { CatMonedaUpdateComponent } from './cat-moneda-update.component';

@Injectable({ providedIn: 'root' })
export class CatMonedaResolve implements Resolve<ICatMoneda> {
  constructor(private service: CatMonedaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatMoneda> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catMoneda: HttpResponse<CatMoneda>) => {
          if (catMoneda.body) {
            return of(catMoneda.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatMoneda());
  }
}

export const catMonedaRoute: Routes = [
  {
    path: '',
    component: CatMonedaComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catMoneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatMonedaDetailComponent,
    resolve: {
      catMoneda: CatMonedaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catMoneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatMonedaUpdateComponent,
    resolve: {
      catMoneda: CatMonedaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catMoneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatMonedaUpdateComponent,
    resolve: {
      catMoneda: CatMonedaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catMoneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
