import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatCP } from 'app/shared/model/cat-cp.model';

@Component({
  selector: 'jhi-cat-cp-detail',
  templateUrl: './cat-cp-detail.component.html',
})
export class CatCPDetailComponent implements OnInit {
  catCP: ICatCP | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catCP }) => (this.catCP = catCP));
  }

  previousState(): void {
    window.history.back();
  }
}
