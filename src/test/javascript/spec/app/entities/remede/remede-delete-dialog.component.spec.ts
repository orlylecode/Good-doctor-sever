import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { RemedeDeleteDialogComponent } from 'app/entities/remede/remede-delete-dialog.component';
import { RemedeService } from 'app/entities/remede/remede.service';

describe('Component Tests', () => {
  describe('Remede Management Delete Component', () => {
    let comp: RemedeDeleteDialogComponent;
    let fixture: ComponentFixture<RemedeDeleteDialogComponent>;
    let service: RemedeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [RemedeDeleteDialogComponent]
      })
        .overrideTemplate(RemedeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RemedeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RemedeService);
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
