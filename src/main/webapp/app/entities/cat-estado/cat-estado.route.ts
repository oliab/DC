import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatEstado, CatEstado } from 'app/shared/model/cat-estado.model';
import { CatEstadoService } from './cat-estado.service';
import { CatEstadoComponent } from './cat-estado.component';
import { CatEstadoDetailComponent } from './cat-estado-detail.component';
import { CatEstadoUpdateComponent } from './cat-estado-update.component';

@Injectable({ providedIn: 'root' })
export class CatEstadoResolve implements Resolve<ICatEstado> {
  constructor(private service: CatEstadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatEstado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catEstado: HttpResponse<CatEstado>) => {
          if (catEstado.body) {
            return of(catEstado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatEstado());
  }
}

export const catEstadoRoute: Routes = [
  {
    path: '',
    component: CatEstadoComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catEstado.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatEstadoDetailComponent,
    resolve: {
      catEstado: CatEstadoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catEstado.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatEstadoUpdateComponent,
    resolve: {
      catEstado: CatEstadoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catEstado.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatEstadoUpdateComponent,
    resolve: {
      catEstado: CatEstadoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catEstado.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
