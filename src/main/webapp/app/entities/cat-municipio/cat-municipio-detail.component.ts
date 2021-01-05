import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';

@Component({
  selector: 'jhi-cat-municipio-detail',
  templateUrl: './cat-municipio-detail.component.html',
})
export class CatMunicipioDetailComponent implements OnInit {
  catMunicipio: ICatMunicipio | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catMunicipio }) => (this.catMunicipio = catMunicipio));
  }

  previousState(): void {
    window.history.back();
  }
}
