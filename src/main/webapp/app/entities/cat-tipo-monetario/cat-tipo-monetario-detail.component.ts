import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';

@Component({
  selector: 'jhi-cat-tipo-monetario-detail',
  templateUrl: './cat-tipo-monetario-detail.component.html',
})
export class CatTipoMonetarioDetailComponent implements OnInit {
  catTipoMonetario: ICatTipoMonetario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoMonetario }) => (this.catTipoMonetario = catTipoMonetario));
  }

  previousState(): void {
    window.history.back();
  }
}
