import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConseil } from 'app/shared/model/conseil.model';
import { ConseilService } from './conseil.service';

@Component({
  selector: 'jhi-conseil-delete-dialog',
  templateUrl: './conseil-delete-dialog.component.html'
})
export class ConseilDeleteDialogComponent {
  conseil: IConseil;

  constructor(protected conseilService: ConseilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.conseilService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'conseilListModification',
        content: 'Deleted an conseil'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-conseil-delete-popup',
  template: ''
})
export class ConseilDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ conseil }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ConseilDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.conseil = conseil;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/conseil', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/conseil', { outlets: { popup: null } }]);
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
