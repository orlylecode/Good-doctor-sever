import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GoodDoctorSeverSharedModule } from 'app/shared/shared.module';
import { MaladieComponent } from './maladie.component';
import { MaladieDetailComponent } from './maladie-detail.component';
import { MaladieUpdateComponent } from './maladie-update.component';
import { MaladieDeletePopupComponent, MaladieDeleteDialogComponent } from './maladie-delete-dialog.component';
import { maladieRoute, maladiePopupRoute } from './maladie.route';

const ENTITY_STATES = [...maladieRoute, ...maladiePopupRoute];

@NgModule({
  imports: [GoodDoctorSeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MaladieComponent,
    MaladieDetailComponent,
    MaladieUpdateComponent,
    MaladieDeleteDialogComponent,
    MaladieDeletePopupComponent
  ],
  entryComponents: [MaladieDeleteDialogComponent]
})
export class GoodDoctorSeverMaladieModule {}
