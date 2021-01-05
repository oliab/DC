import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatTipoDocumento, CatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';
import { CatTipoDocumentoService } from './cat-tipo-documento.service';
import { CatTipoDocumentoComponent } from './cat-tipo-documento.component';
import { CatTipoDocumentoDetailComponent } from './cat-tipo-documento-detail.component';
import { CatTipoDocumentoUpdateComponent } from './cat-tipo-documento-update.component';

@Injectable({ providedIn: 'root' })
export class CatTipoDocumentoResolve implements Resolve<ICatTipoDocumento> {
  constructor(private service: CatTipoDocumentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatTipoDocumento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catTipoDocumento: HttpResponse<CatTipoDocumento>) => {
          if (catTipoDocumento.body) {
            return of(catTipoDocumento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatTipoDocumento());
  }
}

export const catTipoDocumentoRoute: Routes = [
  {
    path: '',
    component: CatTipoDocumentoComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catTipoDocumento.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatTipoDocumentoDetailComponent,
    resolve: {
      catTipoDocumento: CatTipoDocumentoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoDocumento.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatTipoDocumentoUpdateComponent,
    resolve: {
      catTipoDocumento: CatTipoDocumentoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoDocumento.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatTipoDocumentoUpdateComponent,
    resolve: {
      catTipoDocumento: CatTipoDocumentoResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoDocumento.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
