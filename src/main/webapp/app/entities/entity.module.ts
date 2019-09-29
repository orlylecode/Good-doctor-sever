import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'traitement',
        loadChildren: () => import('./traitement/traitement.module').then(m => m.GoodDoctorSeverTraitementModule)
      },
      {
        path: 'symptome',
        loadChildren: () => import('./symptome/symptome.module').then(m => m.GoodDoctorSeverSymptomeModule)
      },
      {
        path: 'remede',
        loadChildren: () => import('./remede/remede.module').then(m => m.GoodDoctorSeverRemedeModule)
      },
      {
        path: 'maladie',
        loadChildren: () => import('./maladie/maladie.module').then(m => m.GoodDoctorSeverMaladieModule)
      },
      {
        path: 'conseil',
        loadChildren: () => import('./conseil/conseil.module').then(m => m.GoodDoctorSeverConseilModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class GoodDoctorSeverEntityModule {}
