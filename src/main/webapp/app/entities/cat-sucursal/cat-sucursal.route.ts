import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatSucursal, CatSucursal } from 'app/shared/model/cat-sucursal.model';
import { CatSucursalService } from './cat-sucursal.service';
import { CatSucursalComponent } from './cat-sucursal.component';
import { CatSucursalDetailComponent } from './cat-sucursal-detail.component';
import { CatSucursalUpdateComponent } from './cat-sucursal-update.component';

@Injectable({ providedIn: 'root' })
export class CatSucursalResolve implements Resolve<ICatSucursal> {
  constructor(private service: CatSucursalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatSucursal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catSucursal: HttpResponse<CatSucursal>) => {
          if (catSucursal.body) {
            return of(catSucursal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatSucursal());
  }
}

export const catSucursalRoute: Routes = [
  {
    path: '',
    component: CatSucursalComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catSucursal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatSucursalDetailComponent,
    resolve: {
      catSucursal: CatSucursalResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catSucursal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatSucursalUpdateComponent,
    resolve: {
      catSucursal: CatSucursalResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catSucursal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatSucursalUpdateComponent,
    resolve: {
      catSucursal: CatSucursalResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catSucursal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
