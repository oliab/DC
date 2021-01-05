import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';

@Component({
  selector: 'jhi-cat-tipo-documento-detail',
  templateUrl: './cat-tipo-documento-detail.component.html',
})
export class CatTipoDocumentoDetailComponent implements OnInit {
  catTipoDocumento: ICatTipoDocumento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoDocumento }) => (this.catTipoDocumento = catTipoDocumento));
  }

  previousState(): void {
    window.history.back();
  }
}
