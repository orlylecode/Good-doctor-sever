import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { RemedeUpdateComponent } from 'app/entities/remede/remede-update.component';
import { RemedeService } from 'app/entities/remede/remede.service';
import { Remede } from 'app/shared/model/remede.model';

describe('Component Tests', () => {
  describe('Remede Management Update Component', () => {
    let comp: RemedeUpdateComponent;
    let fixture: ComponentFixture<RemedeUpdateComponent>;
    let service: RemedeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [RemedeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RemedeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RemedeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemedeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remede(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Remede();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
