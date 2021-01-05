import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDatosUsuario } from 'app/shared/model/datos-usuario.model';

@Component({
  selector: 'jhi-datos-usuario-detail',
  templateUrl: './datos-usuario-detail.component.html',
})
export class DatosUsuarioDetailComponent implements OnInit {
  datosUsuario: IDatosUsuario | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ datosUsuario }) => (this.datosUsuario = datosUsuario));
  }

  previousState(): void {
    window.history.back();
  }
}
