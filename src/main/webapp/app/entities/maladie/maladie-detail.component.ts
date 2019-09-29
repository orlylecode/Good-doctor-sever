import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaladie } from 'app/shared/model/maladie.model';

@Component({
  selector: 'jhi-maladie-detail',
  templateUrl: './maladie-detail.component.html'
})
export class MaladieDetailComponent implements OnInit {
  maladie: IMaladie;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ maladie }) => {
      this.maladie = maladie;
    });
  }

  previousState() {
    window.history.back();
  }
}
