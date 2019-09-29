import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GoodDoctorSeverTestModule } from '../../../test.module';
import { MaladieDeleteDialogComponent } from 'app/entities/maladie/maladie-delete-dialog.component';
import { MaladieService } from 'app/entities/maladie/maladie.service';

describe('Component Tests', () => {
  describe('Maladie Management Delete Component', () => {
    let comp: MaladieDeleteDialogComponent;
    let fixture: ComponentFixture<MaladieDeleteDialogComponent>;
    let service: MaladieService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GoodDoctorSeverTestModule],
        declarations: [MaladieDeleteDialogComponent]
      })
        .overrideTemplate(MaladieDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MaladieDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MaladieService);
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
