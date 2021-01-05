import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatPais } from 'app/shared/model/cat-pais.model';

@Component({
  selector: 'jhi-cat-pais-detail',
  templateUrl: './cat-pais-detail.component.html',
})
export class CatPaisDetailComponent implements OnInit {
  catPais: ICatPais | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catPais }) => (this.catPais = catPais));
  }

  previousState(): void {
    window.history.back();
  }
}
