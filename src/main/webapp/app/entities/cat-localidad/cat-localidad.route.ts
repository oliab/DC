import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatLocalidad, CatLocalidad } from 'app/shared/model/cat-localidad.model';
import { CatLocalidadService } from './cat-localidad.service';
import { CatLocalidadComponent } from './cat-localidad.component';
import { CatLocalidadDetailComponent } from './cat-localidad-detail.component';
import { CatLocalidadUpdateComponent } from './cat-localidad-update.component';

@Injectable({ providedIn: 'root' })
export class CatLocalidadResolve implements Resolve<ICatLocalidad> {
  constructor(private service: CatLocalidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatLocalidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catLocalidad: HttpResponse<CatLocalidad>) => {
          if (catLocalidad.body) {
            return of(catLocalidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatLocalidad());
  }
}

export const catLocalidadRoute: Routes = [
  {
    path: '',
    component: CatLocalidadComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catLocalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatLocalidadDetailComponent,
    resolve: {
      catLocalidad: CatLocalidadResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catLocalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatLocalidadUpdateComponent,
    resolve: {
      catLocalidad: CatLocalidadResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catLocalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatLocalidadUpdateComponent,
    resolve: {
      catLocalidad: CatLocalidadResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catLocalidad.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
