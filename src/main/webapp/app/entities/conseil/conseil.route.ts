import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Conseil } from 'app/shared/model/conseil.model';
import { ConseilService } from './conseil.service';
import { ConseilComponent } from './conseil.component';
import { ConseilDetailComponent } from './conseil-detail.component';
import { ConseilUpdateComponent } from './conseil-update.component';
import { ConseilDeletePopupComponent } from './conseil-delete-dialog.component';
import { IConseil } from 'app/shared/model/conseil.model';

@Injectable({ providedIn: 'root' })
export class ConseilResolve implements Resolve<IConseil> {
  constructor(private service: ConseilService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConseil> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Conseil>) => response.ok),
        map((conseil: HttpResponse<Conseil>) => conseil.body)
      );
    }
    return of(new Conseil());
  }
}

export const conseilRoute: Routes = [
  {
    path: '',
    component: ConseilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'goodDoctorSeverApp.conseil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConseilDetailComponent,
    resolve: {
      conseil: ConseilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.conseil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConseilUpdateComponent,
    resolve: {
      conseil: ConseilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.conseil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConseilUpdateComponent,
    resolve: {
      conseil: ConseilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.conseil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const conseilPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ConseilDeletePopupComponent,
    resolve: {
      conseil: ConseilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.conseil.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
