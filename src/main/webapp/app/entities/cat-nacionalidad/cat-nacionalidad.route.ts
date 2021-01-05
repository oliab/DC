import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatNacionalidad, CatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';
import { CatNacionalidadService } from './cat-nacionalidad.service';
import { CatNacionalidadComponent } from './cat-nacionalidad.component';
import { CatNacionalidadDetailComponent } from './cat-nacionalidad-detail.component';
import { CatNacionalidadUpdateComponent } from './cat-nacionalidad-update.component';

@Injectable({ providedIn: 'root' })
export class CatNacionalidadResolve implements Resolve<ICatNacionalidad> {
  constructor(private service: CatNacionalidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatNacionalidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catNacionalidad: HttpResponse<CatNacionalidad>) => {
          if (catNacionalidad.body) {
            return of(catNacionalidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatNacionalidad());
  }
}

export const catNacionalidadRoute: Routes = [
  {
    path: '',
    component: CatNacionalidadComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catNacionalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatNacionalidadDetailComponent,
    resolve: {
      catNacionalidad: CatNacionalidadResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catNacionalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatNacionalidadUpdateComponent,
    resolve: {
      catNacionalidad: CatNacionalidadResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catNacionalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatNacionalidadUpdateComponent,
    resolve: {
      catNacionalidad: CatNacionalidadResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catNacionalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
