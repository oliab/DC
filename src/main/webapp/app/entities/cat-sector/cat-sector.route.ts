import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatSector, CatSector } from 'app/shared/model/cat-sector.model';
import { CatSectorService } from './cat-sector.service';
import { CatSectorComponent } from './cat-sector.component';
import { CatSectorDetailComponent } from './cat-sector-detail.component';
import { CatSectorUpdateComponent } from './cat-sector-update.component';

@Injectable({ providedIn: 'root' })
export class CatSectorResolve implements Resolve<ICatSector> {
  constructor(private service: CatSectorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatSector> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catSector: HttpResponse<CatSector>) => {
          if (catSector.body) {
            return of(catSector.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatSector());
  }
}

export const catSectorRoute: Routes = [
  {
    path: '',
    component: CatSectorComponent,
    data: {
      authorities: [Authority.SUPERVISOR],
      defaultSort: 'id,asc',
      pageTitle: 'resaMxWebApp.catSector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatSectorDetailComponent,
    resolve: {
      catSector: CatSectorResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catSector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatSectorUpdateComponent,
    resolve: {
      catSector: CatSectorResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catSector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatSectorUpdateComponent,
    resolve: {
      catSector: CatSectorResolve,
    },
    data: {
      authorities: [Authority.SUPERVISOR],
      pageTitle: 'resaMxWebApp.catSector.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
