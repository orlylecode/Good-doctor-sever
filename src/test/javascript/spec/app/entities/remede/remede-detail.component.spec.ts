import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { RemedeDetailComponent } from 'app/entities/remede/remede-detail.component';
import { Remede } from 'app/shared/model/remede.model';

describe('Component Tests', () => {
  describe('Remede Management Detail Component', () => {
    let comp: RemedeDetailComponent;
    let fixture: ComponentFixture<RemedeDetailComponent>;
    const route = ({ data: of({ remede: new Remede(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [RemedeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RemedeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemedeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.remede).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
