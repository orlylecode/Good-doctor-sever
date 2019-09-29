import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { MaladieUpdateComponent } from 'app/entities/maladie/maladie-update.component';
import { MaladieService } from 'app/entities/maladie/maladie.service';
import { Maladie } from 'app/shared/model/maladie.model';

describe('Component Tests', () => {
  describe('Maladie Management Update Component', () => {
    let comp: MaladieUpdateComponent;
    let fixture: ComponentFixture<MaladieUpdateComponent>;
    let service: MaladieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [MaladieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MaladieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MaladieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaladieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Maladie(123);
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
        const entity = new Maladie();
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
