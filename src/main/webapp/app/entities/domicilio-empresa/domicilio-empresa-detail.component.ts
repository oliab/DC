import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';

@Component({
  selector: 'jhi-domicilio-empresa-detail',
  templateUrl: './domicilio-empresa-detail.component.html',
})
export class DomicilioEmpresaDetailComponent implements OnInit {
  domicilioEmpresa: IDomicilioEmpresa | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ domicilioEmpresa }) => (this.domicilioEmpresa = domicilioEmpresa));
  }

  previousState(): void {
    window.history.back();
  }
}
