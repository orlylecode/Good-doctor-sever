import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Symptome } from 'app/shared/model/symptome.model';
import { SymptomeService } from './symptome.service';
import { SymptomeComponent } from './symptome.component';
import { SymptomeDetailComponent } from './symptome-detail.component';
import { SymptomeUpdateComponent } from './symptome-update.component';
import { SymptomeDeletePopupComponent } from './symptome-delete-dialog.component';
import { ISymptome } from 'app/shared/model/symptome.model';

@Injectable({ providedIn: 'root' })
export class SymptomeResolve implements Resolve<ISymptome> {
  constructor(private service: SymptomeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISymptome> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Symptome>) => response.ok),
        map((symptome: HttpResponse<Symptome>) => symptome.body)
      );
    }
    return of(new Symptome());
  }
}

export const symptomeRoute: Routes = [
  {
    path: '',
    component: SymptomeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'goodDoctorSeverApp.symptome.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SymptomeDetailComponent,
    resolve: {
      symptome: SymptomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.symptome.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SymptomeUpdateComponent,
    resolve: {
      symptome: SymptomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.symptome.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SymptomeUpdateComponent,
    resolve: {
      symptome: SymptomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.symptome.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const symptomePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SymptomeDeletePopupComponent,
    resolve: {
      symptome: SymptomeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'goodDoctorSeverApp.symptome.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
