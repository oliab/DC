import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatMoneda } from 'app/shared/model/cat-moneda.model';

@Component({
  selector: 'jhi-cat-moneda-detail',
  templateUrl: './cat-moneda-detail.component.html',
})
export class CatMonedaDetailComponent implements OnInit {
  catMoneda: ICatMoneda | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catMoneda }) => (this.catMoneda = catMoneda));
  }

  previousState(): void {
    window.history.back();
  }
}
