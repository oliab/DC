import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatTipoEmpresa, CatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';
import { CatTipoEmpresaService } from './cat-tipo-empresa.service';
import { CatTipoEmpresaComponent } from './cat-tipo-empresa.component';
import { CatTipoEmpresaDetailComponent } from './cat-tipo-empresa-detail.component';
import { CatTipoEmpresaUpdateComponent } from './cat-tipo-empresa-update.component';

@Injectable({ providedIn: 'root' })
export class CatTipoEmpresaResolve implements Resolve<ICatTipoEmpresa> {
  constructor(private service: CatTipoEmpresaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatTipoEmpresa> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catTipoEmpresa: HttpResponse<CatTipoEmpresa>) => {
          if (catTipoEmpresa.body) {
            return of(catTipoEmpresa.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatTipoEmpresa());
  }
}

export const catTipoEmpresaRoute: Routes = [
  {
    path: '',
    component: CatTipoEmpresaComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catTipoEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatTipoEmpresaDetailComponent,
    resolve: {
      catTipoEmpresa: CatTipoEmpresaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatTipoEmpresaUpdateComponent,
    resolve: {
      catTipoEmpresa: CatTipoEmpresaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatTipoEmpresaUpdateComponent,
    resolve: {
      catTipoEmpresa: CatTipoEmpresaResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoEmpresa.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
