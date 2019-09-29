import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISymptome, Symptome } from 'app/shared/model/symptome.model';
import { SymptomeService } from './symptome.service';
import { IMaladie } from 'app/shared/model/maladie.model';
import { MaladieService } from 'app/entities/maladie/maladie.service';

@Component({
  selector: 'jhi-symptome-update',
  templateUrl: './symptome-update.component.html'
})
export class SymptomeUpdateComponent implements OnInit {
  isSaving: boolean;

  maladies: IMaladie[];

  editForm = this.fb.group({
    id: [],
    nom: [],
    description: [],
    effet: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected symptomeService: SymptomeService,
    protected maladieService: MaladieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ symptome }) => {
      this.updateForm(symptome);
    });
    this.maladieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMaladie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMaladie[]>) => response.body)
      )
      .subscribe((res: IMaladie[]) => (this.maladies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(symptome: ISymptome) {
    this.editForm.patchValue({
      id: symptome.id,
      nom: symptome.nom,
      description: symptome.description,
      effet: symptome.effet
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const symptome = this.createFromForm();
    if (symptome.id !== undefined) {
      this.subscribeToSaveResponse(this.symptomeService.update(symptome));
    } else {
      this.subscribeToSaveResponse(this.symptomeService.create(symptome));
    }
  }

  private createFromForm(): ISymptome {
    return {
      ...new Symptome(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      effet: this.editForm.get(['effet']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISymptome>>) {
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
