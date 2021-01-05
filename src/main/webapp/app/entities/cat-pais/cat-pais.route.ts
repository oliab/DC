import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatPais, CatPais } from 'app/shared/model/cat-pais.model';
import { CatPaisService } from './cat-pais.service';
import { CatPaisComponent } from './cat-pais.component';
import { CatPaisDetailComponent } from './cat-pais-detail.component';
import { CatPaisUpdateComponent } from './cat-pais-update.component';

@Injectable({ providedIn: 'root' })
export class CatPaisResolve implements Resolve<ICatPais> {
  constructor(private service: CatPaisService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatPais> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catPais: HttpResponse<CatPais>) => {
          if (catPais.body) {
            return of(catPais.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatPais());
  }
}

export const catPaisRoute: Routes = [
  {
    path: '',
    component: CatPaisComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catPais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatPaisDetailComponent,
    resolve: {
      catPais: CatPaisResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catPais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatPaisUpdateComponent,
    resolve: {
      catPais: CatPaisResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catPais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatPaisUpdateComponent,
    resolve: {
      catPais: CatPaisResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catPais.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
