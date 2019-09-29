import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRemede } from 'app/shared/model/remede.model';
import { RemedeService } from './remede.service';

@Component({
  selector: 'jhi-remede-delete-dialog',
  templateUrl: './remede-delete-dialog.component.html'
})
export class RemedeDeleteDialogComponent {
  remede: IRemede;

  constructor(protected remedeService: RemedeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.remedeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'remedeListModification',
        content: 'Deleted an remede'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-remede-delete-popup',
  template: ''
})
export class RemedeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ remede }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RemedeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.remede = remede;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/remede', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/remede', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
