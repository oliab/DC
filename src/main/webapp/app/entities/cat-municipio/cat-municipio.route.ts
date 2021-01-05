import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatMunicipio, CatMunicipio } from 'app/shared/model/cat-municipio.model';
import { CatMunicipioService } from './cat-municipio.service';
import { CatMunicipioComponent } from './cat-municipio.component';
import { CatMunicipioDetailComponent } from './cat-municipio-detail.component';
import { CatMunicipioUpdateComponent } from './cat-municipio-update.component';

@Injectable({ providedIn: 'root' })
export class CatMunicipioResolve implements Resolve<ICatMunicipio> {
  constructor(private service: CatMunicipioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatMunicipio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catMunicipio: HttpResponse<CatMunicipio>) => {
          if (catMunicipio.body) {
            return of(catMunicipio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatMunicipio());
  }
}

export const catMunicipioRoute: Routes = [
  {
    path: '',
    component: CatMunicipioComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catMunicipio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatMunicipioDetailComponent,
    resolve: {
      catMunicipio: CatMunicipioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catMunicipio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatMunicipioUpdateComponent,
    resolve: {
      catMunicipio: CatMunicipioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catMunicipio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatMunicipioUpdateComponent,
    resolve: {
      catMunicipio: CatMunicipioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catMunicipio.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
