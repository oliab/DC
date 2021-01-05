import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatTipoOperacion } from 'app/shared/model/cat-tipo-operacion.model';

@Component({
  selector: 'jhi-cat-tipo-operacion-detail',
  templateUrl: './cat-tipo-operacion-detail.component.html',
})
export class CatTipoOperacionDetailComponent implements OnInit {
  catTipoOperacion: ICatTipoOperacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoOperacion }) => (this.catTipoOperacion = catTipoOperacion));
  }

  previousState(): void {
    window.history.back();
  }
}
