import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatSucursal } from 'app/shared/model/cat-sucursal.model';

@Component({
  selector: 'jhi-cat-sucursal-detail',
  templateUrl: './cat-sucursal-detail.component.html',
})
export class CatSucursalDetailComponent implements OnInit {
  catSucursal: ICatSucursal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catSucursal }) => (this.catSucursal = catSucursal));
  }

  previousState(): void {
    window.history.back();
  }
}
