import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IConseil, Conseil } from 'app/shared/model/conseil.model';
import { ConseilService } from './conseil.service';
import { IMaladie } from 'app/shared/model/maladie.model';
import { MaladieService } from 'app/entities/maladie/maladie.service';

@Component({
  selector: 'jhi-conseil-update',
  templateUrl: './conseil-update.component.html'
})
export class ConseilUpdateComponent implements OnInit {
  isSaving: boolean;

  maladies: IMaladie[];

  editForm = this.fb.group({
    id: [],
    nom: [],
    auteur: [],
    conseil: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected conseilService: ConseilService,
    protected maladieService: MaladieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ conseil }) => {
      this.updateForm(conseil);
    });
    this.maladieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMaladie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMaladie[]>) => response.body)
      )
      .subscribe((res: IMaladie[]) => (this.maladies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(conseil: IConseil) {
    this.editForm.patchValue({
      id: conseil.id,
      nom: conseil.nom,
      auteur: conseil.auteur,
      conseil: conseil.conseil
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const conseil = this.createFromForm();
    if (conseil.id !== undefined) {
      this.subscribeToSaveResponse(this.conseilService.update(conseil));
    } else {
      this.subscribeToSaveResponse(this.conseilService.create(conseil));
    }
  }

  private createFromForm(): IConseil {
    return {
      ...new Conseil(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      auteur: this.editForm.get(['auteur']).value,
      conseil: this.editForm.get(['conseil']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConseil>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackMaladieById(index: number, item: IMaladie) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
