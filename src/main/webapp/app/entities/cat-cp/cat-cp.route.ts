import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatCP, CatCP } from 'app/shared/model/cat-cp.model';
import { CatCPService } from './cat-cp.service';
import { CatCPComponent } from './cat-cp.component';
import { CatCPDetailComponent } from './cat-cp-detail.component';
import { CatCPUpdateComponent } from './cat-cp-update.component';

@Injectable({ providedIn: 'root' })
export class CatCPResolve implements Resolve<ICatCP> {
  constructor(private service: CatCPService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatCP> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catCP: HttpResponse<CatCP>) => {
          if (catCP.body) {
            return of(catCP.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatCP());
  }
}

export const catCPRoute: Routes = [
  {
    path: '',
    component: CatCPComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catCP.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatCPDetailComponent,
    resolve: {
      catCP: CatCPResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catCP.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatCPUpdateComponent,
    resolve: {
      catCP: CatCPResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catCP.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatCPUpdateComponent,
    resolve: {
      catCP: CatCPResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catCP.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
