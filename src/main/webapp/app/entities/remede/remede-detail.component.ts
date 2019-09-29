import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemede } from 'app/shared/model/remede.model';

@Component({
  selector: 'jhi-remede-detail',
  templateUrl: './remede-detail.component.html'
})
export class RemedeDetailComponent implements OnInit {
  remede: IRemede;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ remede }) => {
      this.remede = remede;
    });
  }

  previousState() {
    window.history.back();
  }
}
