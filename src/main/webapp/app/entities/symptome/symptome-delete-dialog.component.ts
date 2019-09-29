import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISymptome } from 'app/shared/model/symptome.model';
import { SymptomeService } from './symptome.service';

@Component({
  selector: 'jhi-symptome-delete-dialog',
  templateUrl: './symptome-delete-dialog.component.html'
})
export class SymptomeDeleteDialogComponent {
  symptome: ISymptome;

  constructor(protected symptomeService: SymptomeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.symptomeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'symptomeListModification',
        content: 'Deleted an symptome'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-symptome-delete-popup',
  template: ''
})
export class SymptomeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ symptome }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SymptomeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.symptome = symptome;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/symptome', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/symptome', { outlets: { popup: null } }]);
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
