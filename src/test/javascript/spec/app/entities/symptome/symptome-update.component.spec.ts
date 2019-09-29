import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { SymptomeUpdateComponent } from 'app/entities/symptome/symptome-update.component';
import { SymptomeService } from 'app/entities/symptome/symptome.service';
import { Symptome } from 'app/shared/model/symptome.model';

describe('Component Tests', () => {
  describe('Symptome Management Update Component', () => {
    let comp: SymptomeUpdateComponent;
    let fixture: ComponentFixture<SymptomeUpdateComponent>;
    let service: SymptomeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [SymptomeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SymptomeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SymptomeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Symptome(123);
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
        const entity = new Symptome();
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
