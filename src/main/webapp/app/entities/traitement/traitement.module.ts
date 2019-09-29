import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GoodDoctorSeverSharedModule } from 'app/shared/shared.module';
import { TraitementComponent } from './traitement.component';
import { TraitementDetailComponent } from './traitement-detail.component';
import { TraitementUpdateComponent } from './traitement-update.component';
import { TraitementDeletePopupComponent, TraitementDeleteDialogComponent } from './traitement-delete-dialog.component';
import { traitementRoute, traitementPopupRoute } from './traitement.route';

const ENTITY_STATES = [...traitementRoute, ...traitementPopupRoute];

@NgModule({
  imports: [GoodDoctorSeverSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TraitementComponent,
    TraitementDetailComponent,
    TraitementUpdateComponent,
    TraitementDeleteDialogComponent,
    TraitementDeletePopupComponent
  ],
  entryComponents: [TraitementDeleteDialogComponent]
})
export class GoodDoctorSeverTraitementModule {}
