import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GoodDoctorSeverSharedModule } from 'app/shared/shared.module';
import { SymptomeComponent } from './symptome.component';
import { SymptomeDetailComponent } from './symptome-detail.component';
import { SymptomeUpdateComponent } from './symptome-update.component';
import { SymptomeDeletePopupComponent, SymptomeDeleteDialogComponent } from './symptome-delete-dialog.component';
import { symptomeRoute, symptomePopupRoute } from './symptome.route';

const ENTITY_STATES = [...symptomeRoute, ...symptomePopupRoute];

@NgModule({
  imports: [GoodDoctorSeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SymptomeComponent,
    SymptomeDetailComponent,
    SymptomeUpdateComponent,
    SymptomeDeleteDialogComponent,
    SymptomeDeletePopupComponent
  ],
  entryComponents: [SymptomeDeleteDialogComponent]
})
export class GoodDoctorSeverSymptomeModule {}
