import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaladie } from 'app/shared/model/maladie.model';
import { MaladieService } from './maladie.service';

@Component({
  selector: 'jhi-maladie-delete-dialog',
  templateUrl: './maladie-delete-dialog.component.html'
})
export class MaladieDeleteDialogComponent {
  maladie: IMaladie;

  constructor(protected maladieService: MaladieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.maladieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'maladieListModification',
        content: 'Deleted an maladie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-maladie-delete-popup',
  template: ''
})
export class MaladieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ maladie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MaladieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.maladie = maladie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/maladie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/maladie', { outlets: { popup: null } }]);
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
