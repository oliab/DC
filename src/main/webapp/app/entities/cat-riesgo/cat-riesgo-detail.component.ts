import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatRiesgo } from 'app/shared/model/cat-riesgo.model';

@Component({
  selector: 'jhi-cat-riesgo-detail',
  templateUrl: './cat-riesgo-detail.component.html',
})
export class CatRiesgoDetailComponent implements OnInit {
  catRiesgo: ICatRiesgo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catRiesgo }) => (this.catRiesgo = catRiesgo));
  }

  previousState(): void {
    window.history.back();
  }
}
