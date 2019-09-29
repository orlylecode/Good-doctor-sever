import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITraitement, Traitement } from 'app/shared/model/traitement.model';
import { TraitementService } from './traitement.service';
import { IRemede } from 'app/shared/model/remede.model';
import { RemedeService } from 'app/entities/remede/remede.service';
import { IMaladie } from 'app/shared/model/maladie.model';
import { MaladieService } from 'app/entities/maladie/maladie.service';

@Component({
  selector: 'jhi-traitement-update',
  templateUrl: './traitement-update.component.html'
})
export class TraitementUpdateComponent implements OnInit {
  isSaving: boolean;

  remedes: IRemede[];

  maladies: IMaladie[];

  editForm = this.fb.group({
    id: [],
    nom: [],
    auteur: [],
    description: [],
    remedes: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected traitementService: TraitementService,
    protected remedeService: RemedeService,
    protected maladieService: MaladieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ traitement }) => {
      this.updateForm(traitement);
    });
    this.remedeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRemede[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRemede[]>) => response.body)
      )
      .subscribe((res: IRemede[]) => (this.remedes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.maladieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMaladie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMaladie[]>) => response.body)
      )
      .subscribe((res: IMaladie[]) => (this.maladies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(traitement: ITraitement) {
    this.editForm.patchValue({
      id: traitement.id,
      nom: traitement.nom,
      auteur: traitement.auteur,
      description: traitement.description,
      remedes: traitement.remedes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const traitement = this.createFromForm();
    if (traitement.id !== undefined) {
      this.subscribeToSaveResponse(this.traitementService.update(traitement));
    } else {
      this.subscribeToSaveResponse(this.traitementService.create(traitement));
    }
  }

  private createFromForm(): ITraitement {
    return {
      ...new Traitement(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      auteur: this.editForm.get(['auteur']).value,
      description: this.editForm.get(['description']).value,
      remedes: this.editForm.get(['remedes']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraitement>>) {
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

  trackRemedeById(index: number, item: IRemede) {
    return item.id;
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
