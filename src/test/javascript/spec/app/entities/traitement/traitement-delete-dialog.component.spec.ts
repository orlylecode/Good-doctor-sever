import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { TraitementDeleteDialogComponent } from 'app/entities/traitement/traitement-delete-dialog.component';
import { TraitementService } from 'app/entities/traitement/traitement.service';

describe('Component Tests', () => {
  describe('Traitement Management Delete Component', () => {
    let comp: TraitementDeleteDialogComponent;
    let fixture: ComponentFixture<TraitementDeleteDialogComponent>;
    let service: TraitementService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [TraitementDeleteDialogComponent]
      })
        .overrideTemplate(TraitementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TraitementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TraitementService);
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
