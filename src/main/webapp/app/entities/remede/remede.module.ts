import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GoodDoctorSeverSharedModule } from 'app/shared/shared.module';
import { RemedeComponent } from './remede.component';
import { RemedeDetailComponent } from './remede-detail.component';
import { RemedeUpdateComponent } from './remede-update.component';
import { RemedeDeletePopupComponent, RemedeDeleteDialogComponent } from './remede-delete-dialog.component';
import { remedeRoute, remedePopupRoute } from './remede.route';

const ENTITY_STATES = [...remedeRoute, ...remedePopupRoute];

@NgModule({
  imports: [GoodDoctorSeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RemedeComponent, RemedeDetailComponent, RemedeUpdateComponent, RemedeDeleteDialogComponent, RemedeDeletePopupComponent],
  entryComponents: [RemedeDeleteDialogComponent]
})
export class GoodDoctorSeverRemedeModule {}
