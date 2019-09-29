import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { ConseilUpdateComponent } from 'app/entities/conseil/conseil-update.component';
import { ConseilService } from 'app/entities/conseil/conseil.service';
import { Conseil } from 'app/shared/model/conseil.model';

describe('Component Tests', () => {
  describe('Conseil Management Update Component', () => {
    let comp: ConseilUpdateComponent;
    let fixture: ComponentFixture<ConseilUpdateComponent>;
    let service: ConseilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [ConseilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ConseilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConseilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConseilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Conseil(123);
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
        const entity = new Conseil();
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
