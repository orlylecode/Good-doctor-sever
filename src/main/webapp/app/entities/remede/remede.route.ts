import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Remede } from 'app/shared/model/remede.model';
import { RemedeService } from './remede.service';
import { RemedeComponent } from './remede.component';
import { RemedeDetailComponent } from './remede-detail.component';
import { RemedeUpdateComponent } from './remede-update.component';
import { RemedeDeletePopupComponent } from './remede-delete-dialog.component';
import { IRemede } from 'app/shared/model/remede.model';

@Injectable({ providedIn: 'root' })
export class RemedeResolve implements Resolve<IRemede> {
  constructor(private service: RemedeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRemede> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Remede>) => response.ok),
        map((remede: HttpResponse<Remede>) => remede.body)
      );
    }
    return of(new Remede());
  }
}

export const remedeRoute: Routes = [
  {
    path: '',
    component: RemedeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'goodDoctorSeverApp.remede.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RemedeDetailComponent,
    resolve: {
      remede: RemedeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.remede.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RemedeUpdateComponent,
    resolve: {
      remede: RemedeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.remede.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RemedeUpdateComponent,
    resolve: {
      remede: RemedeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.remede.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const remedePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RemedeDeletePopupComponent,
    resolve: {
      remede: RemedeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.remede.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
