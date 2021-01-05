import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatTipoMonetario, CatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';
import { CatTipoMonetarioService } from './cat-tipo-monetario.service';
import { CatTipoMonetarioComponent } from './cat-tipo-monetario.component';
import { CatTipoMonetarioDetailComponent } from './cat-tipo-monetario-detail.component';
import { CatTipoMonetarioUpdateComponent } from './cat-tipo-monetario-update.component';

@Injectable({ providedIn: 'root' })
export class CatTipoMonetarioResolve implements Resolve<ICatTipoMonetario> {
  constructor(private service: CatTipoMonetarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatTipoMonetario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catTipoMonetario: HttpResponse<CatTipoMonetario>) => {
          if (catTipoMonetario.body) {
            return of(catTipoMonetario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatTipoMonetario());
  }
}

export const catTipoMonetarioRoute: Routes = [
  {
    path: '',
    component: CatTipoMonetarioComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catTipoMonetario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatTipoMonetarioDetailComponent,
    resolve: {
      catTipoMonetario: CatTipoMonetarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoMonetario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatTipoMonetarioUpdateComponent,
    resolve: {
      catTipoMonetario: CatTipoMonetarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoMonetario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatTipoMonetarioUpdateComponent,
    resolve: {
      catTipoMonetario: CatTipoMonetarioResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catTipoMonetario.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
