import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { SymptomeDetailComponent } from 'app/entities/symptome/symptome-detail.component';
import { Symptome } from 'app/shared/model/symptome.model';

describe('Component Tests', () => {
  describe('Symptome Management Detail Component', () => {
    let comp: SymptomeDetailComponent;
    let fixture: ComponentFixture<SymptomeDetailComponent>;
    const route = ({ data: of({ symptome: new Symptome(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [SymptomeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SymptomeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.symptome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
