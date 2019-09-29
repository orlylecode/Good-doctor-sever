import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISymptome } from 'app/shared/model/symptome.model';

@Component({
  selector: 'jhi-symptome-detail',
  templateUrl: './symptome-detail.component.html'
})
export class SymptomeDetailComponent implements OnInit {
  symptome: ISymptome;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ symptome }) => {
      this.symptome = symptome;
    });
  }

  previousState() {
    window.history.back();
  }
}
