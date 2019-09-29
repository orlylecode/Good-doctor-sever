import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Traitement } from 'app/shared/model/traitement.model';
import { TraitementService } from './traitement.service';
import { TraitementComponent } from './traitement.component';
import { TraitementDetailComponent } from './traitement-detail.component';
import { TraitementUpdateComponent } from './traitement-update.component';
import { TraitementDeletePopupComponent } from './traitement-delete-dialog.component';
import { ITraitement } from 'app/shared/model/traitement.model';

@Injectable({ providedIn: 'root' })
export class TraitementResolve implements Resolve<ITraitement> {
  constructor(private service: TraitementService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITraitement> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Traitement>) => response.ok),
        map((traitement: HttpResponse<Traitement>) => traitement.body)
      );
    }
    return of(new Traitement());
  }
}

export const traitementRoute: Routes = [
  {
    path: '',
    component: TraitementComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'goodDoctorSeverApp.traitement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TraitementDetailComponent,
    resolve: {
      traitement: TraitementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.traitement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TraitementUpdateComponent,
    resolve: {
      traitement: TraitementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.traitement.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TraitementUpdateComponent,
    resolve: {
      traitement: TraitementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.traitement.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const traitementPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TraitementDeletePopupComponent,
    resolve: {
      traitement: TraitementResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.traitement.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
