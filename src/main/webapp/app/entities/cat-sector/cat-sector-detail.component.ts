import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatSector } from 'app/shared/model/cat-sector.model';

@Component({
  selector: 'jhi-cat-sector-detail',
  templateUrl: './cat-sector-detail.component.html',
})
export class CatSectorDetailComponent implements OnInit {
  catSector: ICatSector | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catSector }) => (this.catSector = catSector));
  }

  previousState(): void {
    window.history.back();
  }
}
