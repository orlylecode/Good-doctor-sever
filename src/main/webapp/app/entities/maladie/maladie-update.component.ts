import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMaladie, Maladie } from 'app/shared/model/maladie.model';
import { MaladieService } from './maladie.service';
import { IConseil } from 'app/shared/model/conseil.model';
import { ConseilService } from 'app/entities/conseil/conseil.service';
import { ITraitement } from 'app/shared/model/traitement.model';
import { TraitementService } from 'app/entities/traitement/traitement.service';
import { ISymptome } from 'app/shared/model/symptome.model';
import { SymptomeService } from 'app/entities/symptome/symptome.service';

@Component({
  selector: 'jhi-maladie-update',
  templateUrl: './maladie-update.component.html'
})
export class MaladieUpdateComponent implements OnInit {
  isSaving: boolean;

  conseils: IConseil[];

  traitements: ITraitement[];

  symptomes: ISymptome[];

  editForm = this.fb.group({
    id: [],
    nom: [],
    description: [],
    type: [],
    conseils: [],
    traitements: [],
    symptomes: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected maladieService: MaladieService,
    protected conseilService: ConseilService,
    protected traitementService: TraitementService,
    protected symptomeService: SymptomeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ maladie }) => {
      this.updateForm(maladie);
    });
    this.conseilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IConseil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IConseil[]>) => response.body)
      )
      .subscribe((res: IConseil[]) => (this.conseils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.traitementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITraitement[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITraitement[]>) => response.body)
      )
      .subscribe((res: ITraitement[]) => (this.traitements = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.symptomeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISymptome[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISymptome[]>) => response.body)
      )
      .subscribe((res: ISymptome[]) => (this.symptomes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(maladie: IMaladie) {
    this.editForm.patchValue({
      id: maladie.id,
      nom: maladie.nom,
      description: maladie.description,
      type: maladie.type,
      conseils: maladie.conseils,
      traitements: maladie.traitements,
      symptomes: maladie.symptomes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const maladie = this.createFromForm();
    if (maladie.id !== undefined) {
      this.subscribeToSaveResponse(this.maladieService.update(maladie));
    } else {
      this.subscribeToSaveResponse(this.maladieService.create(maladie));
    }
  }

  private createFromForm(): IMaladie {
    return {
      ...new Maladie(),
      id: this.editForm.get(['id']).value,
      nom: this.editForm.get(['nom']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      conseils: this.editForm.get(['conseils']).value,
      traitements: this.editForm.get(['traitements']).value,
      symptomes: this.editForm.get(['symptomes']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMaladie>>) {
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

  trackConseilById(index: number, item: IConseil) {
    return item.id;
  }

  trackTraitementById(index: number, item: ITraitement) {
    return item.id;
  }

  trackSymptomeById(index: number, item: ISymptome) {
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
