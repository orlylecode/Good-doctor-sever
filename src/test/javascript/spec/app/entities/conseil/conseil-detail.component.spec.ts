import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { ConseilDetailComponent } from 'app/entities/conseil/conseil-detail.component';
import { Conseil } from 'app/shared/model/conseil.model';

describe('Component Tests', () => {
  describe('Conseil Management Detail Component', () => {
    let comp: ConseilDetailComponent;
    let fixture: ComponentFixture<ConseilDetailComponent>;
    const route = ({ data: of({ conseil: new Conseil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [ConseilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConseilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConseilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conseil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
