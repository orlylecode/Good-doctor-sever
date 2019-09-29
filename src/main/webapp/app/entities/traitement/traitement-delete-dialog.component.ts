import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITraitement } from 'app/shared/model/traitement.model';
import { TraitementService } from './traitement.service';

@Component({
  selector: 'jhi-traitement-delete-dialog',
  templateUrl: './traitement-delete-dialog.component.html'
})
export class TraitementDeleteDialogComponent {
  traitement: ITraitement;

  constructor(
    protected traitementService: TraitementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.traitementService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'traitementListModification',
        content: 'Deleted an traitement'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-traitement-delete-popup',
  template: ''
})
export class TraitementDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ traitement }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TraitementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.traitement = traitement;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/traitement', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/traitement', { outlets: { popup: null } }]);
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
