import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatEstado } from 'app/shared/model/cat-estado.model';

@Component({
  selector: 'jhi-cat-estado-detail',
  templateUrl: './cat-estado-detail.component.html',
})
export class CatEstadoDetailComponent implements OnInit {
  catEstado: ICatEstado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catEstado }) => (this.catEstado = catEstado));
  }

  previousState(): void {
    window.history.back();
  }
}
