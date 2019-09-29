import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GoodDoctorSeverSharedModule } from 'app/shared/shared.module';
import { ConseilComponent } from './conseil.component';
import { ConseilDetailComponent } from './conseil-detail.component';
import { ConseilUpdateComponent } from './conseil-update.component';
import { ConseilDeletePopupComponent, ConseilDeleteDialogComponent } from './conseil-delete-dialog.component';
import { conseilRoute, conseilPopupRoute } from './conseil.route';

const ENTITY_STATES = [...conseilRoute, ...conseilPopupRoute];

@NgModule({
  imports: [GoodDoctorSeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ConseilComponent,
    ConseilDetailComponent,
    ConseilUpdateComponent,
    ConseilDeleteDialogComponent,
    ConseilDeletePopupComponent
  ],
  entryComponents: [ConseilDeleteDialogComponent]
})
export class GoodDoctorSeverConseilModule {}
