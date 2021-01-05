import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';

@Component({
  selector: 'jhi-cat-tipo-empresa-detail',
  templateUrl: './cat-tipo-empresa-detail.component.html',
})
export class CatTipoEmpresaDetailComponent implements OnInit {
  catTipoEmpresa: ICatTipoEmpresa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoEmpresa }) => (this.catTipoEmpresa = catTipoEmpresa));
  }

  previousState(): void {
    window.history.back();
  }
}
