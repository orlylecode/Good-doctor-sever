import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { MaladieDetailComponent } from 'app/entities/maladie/maladie-detail.component';
import { Maladie } from 'app/shared/model/maladie.model';

describe('Component Tests', () => {
  describe('Maladie Management Detail Component', () => {
    let comp: MaladieDetailComponent;
    let fixture: ComponentFixture<MaladieDetailComponent>;
    const route = ({ data: of({ maladie: new Maladie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [MaladieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MaladieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaladieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.maladie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
