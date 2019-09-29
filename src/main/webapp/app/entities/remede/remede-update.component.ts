import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRemede, Remede } from 'app/shared/model/remede.model';
import { RemedeService } from './remede.service';
import { ITraitement } from 'app/shared/model/traitement.model';
import { TraitementService } from 'app/entities/traitement/traitement.service';

@Component({
  selector: 'jhi-remede-update',
  templateUrl: './remede-update.component.html'
})
export class RemedeUpdateComponent implements OnInit {
  isSaving: boolean;

  traitements: ITraitement[];

  editForm = this.fb.group({
    id: [],
    nom: [],
    composition: [],
    possologie: [],
    prevention: [],
    prix: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected remedeService: RemedeService,
    protected traitementService: TraitementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ remede }) => {
      this.updateForm(remede);
    });
    this.traitementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITraitement[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITraitement[]>) => response.body)
      )
      .subscribe((res: ITraitement[]) => (this.traitements = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(remede: IRemede) {
    this.editForm.patchValue({
      id: remede.id,
      nom: remede.nom,
      composition: remede.composition,
      possologie: remede.possologie,
      prevention: remede.prevention,
      prix: remede.prix
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const remede = this.createFromForm();
    if (remede.id !== undefined) {
      this.subscribeToSaveResponse(this.remedeService.update(remede));
    } else {
      this.subscribeToSaveResponse(this.remedeService.create(remede));
    }
  }

  private createFromForm(): IRemede {
    return {
      ...new Remede(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      composition: this.editForm.get(['composition']).value,
      possologie: this.editForm.get(['possologie']).value,
      prevention: this.editForm.get(['prevention']).value,
      prix: this.editForm.get(['prix']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRemede>>) {
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

  trackTraitementById(index: number, item: ITraitement) {
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
