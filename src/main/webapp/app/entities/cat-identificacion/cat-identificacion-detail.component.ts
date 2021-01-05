import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';

@Component({
  selector: 'jhi-cat-identificacion-detail',
  templateUrl: './cat-identificacion-detail.component.html',
})
export class CatIdentificacionDetailComponent implements OnInit {
  catIdentificacion: ICatIdentificacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catIdentificacion }) => (this.catIdentificacion = catIdentificacion));
  }

  previousState(): void {
    window.history.back();
  }
}
