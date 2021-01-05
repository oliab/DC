import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

@Component({
  selector: 'jhi-domicilio-usuario-detail',
  templateUrl: './domicilio-usuario-detail.component.html',
})
export class DomicilioUsuarioDetailComponent implements OnInit {
  domicilioUsuario: IDomicilioUsuario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ domicilioUsuario }) => (this.domicilioUsuario = domicilioUsuario));
  }

  previousState(): void {
    window.history.back();
  }
}
