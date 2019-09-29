import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConseil } from 'app/shared/model/conseil.model';

@Component({
  selector: 'jhi-conseil-detail',
  templateUrl: './conseil-detail.component.html'
})
export class ConseilDetailComponent implements OnInit {
  conseil: IConseil;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ conseil }) => {
      this.conseil = conseil;
    });
  }

  previousState() {
    window.history.back();
  }
}
