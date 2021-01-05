import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatLocalidad } from 'app/shared/model/cat-localidad.model';

@Component({
  selector: 'jhi-cat-localidad-detail',
  templateUrl: './cat-localidad-detail.component.html',
})
export class CatLocalidadDetailComponent implements OnInit {
  catLocalidad: ICatLocalidad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catLocalidad }) => (this.catLocalidad = catLocalidad));
  }

  previousState(): void {
    window.history.back();
  }
}
