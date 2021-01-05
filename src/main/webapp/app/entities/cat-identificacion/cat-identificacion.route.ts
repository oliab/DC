import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatIdentificacion, CatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { CatIdentificacionService } from './cat-identificacion.service';
import { CatIdentificacionComponent } from './cat-identificacion.component';
import { CatIdentificacionDetailComponent } from './cat-identificacion-detail.component';
import { CatIdentificacionUpdateComponent } from './cat-identificacion-update.component';

@Injectable({ providedIn: 'root' })
export class CatIdentificacionResolve implements Resolve<ICatIdentificacion> {
  constructor(private service: CatIdentificacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatIdentificacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catIdentificacion: HttpResponse<CatIdentificacion>) => {
          if (catIdentificacion.body) {
            return of(catIdentificacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatIdentificacion());
  }
}

export const catIdentificacionRoute: Routes = [
  {
    path: '',
    component: CatIdentificacionComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catIdentificacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatIdentificacionDetailComponent,
    resolve: {
      catIdentificacion: CatIdentificacionResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catIdentificacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatIdentificacionUpdateComponent,
    resolve: {
      catIdentificacion: CatIdentificacionResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catIdentificacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatIdentificacionUpdateComponent,
    resolve: {
      catIdentificacion: CatIdentificacionResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catIdentificacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
