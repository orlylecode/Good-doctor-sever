import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { SymptomeDeleteDialogComponent } from 'app/entities/symptome/symptome-delete-dialog.component';
import { SymptomeService } from 'app/entities/symptome/symptome.service';

describe('Component Tests', () => {
  describe('Symptome Management Delete Component', () => {
    let comp: SymptomeDeleteDialogComponent;
    let fixture: ComponentFixture<SymptomeDeleteDialogComponent>;
    let service: SymptomeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [SymptomeDeleteDialogComponent]
      })
        .overrideTemplate(SymptomeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SymptomeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SymptomeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
