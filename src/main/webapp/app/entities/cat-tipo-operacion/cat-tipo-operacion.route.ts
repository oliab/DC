import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatTipoOperacion, CatTipoOperacion } from 'app/shared/model/cat-tipo-operacion.model';
import { CatTipoOperacionService } from './cat-tipo-operacion.service';
import { CatTipoOperacionComponent } from './cat-tipo-operacion.component';
import { CatTipoOperacionDetailComponent } from './cat-tipo-operacion-detail.component';
import { CatTipoOperacionUpdateComponent } from './cat-tipo-operacion-update.component';

@Injectable({ providedIn: 'root' })
export class CatTipoOperacionResolve implements Resolve<ICatTipoOperacion> {
  constructor(private service: CatTipoOperacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatTipoOperacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catTipoOperacion: HttpResponse<CatTipoOperacion>) => {
          if (catTipoOperacion.body) {
            return of(catTipoOperacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatTipoOperacion());
  }
}

export const catTipoOperacionRoute: Routes = [
  {
    path: '',
    component: CatTipoOperacionComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catTipoOperacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatTipoOperacionDetailComponent,
    resolve: {
      catTipoOperacion: CatTipoOperacionResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoOperacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatTipoOperacionUpdateComponent,
    resolve: {
      catTipoOperacion: CatTipoOperacionResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoOperacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatTipoOperacionUpdateComponent,
    resolve: {
      catTipoOperacion: CatTipoOperacionResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoOperacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
