import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';

@Component({
  selector: 'jhi-cat-nacionalidad-detail',
  templateUrl: './cat-nacionalidad-detail.component.html',
})
export class CatNacionalidadDetailComponent implements OnInit {
  catNacionalidad: ICatNacionalidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catNacionalidad }) => (this.catNacionalidad = catNacionalidad));
  }

  previousState(): void {
    window.history.back();
  }
}
