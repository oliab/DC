import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDatosUsuario } from 'app/shared/model/datos-usuario.model';
import { DatosUsuarioService } from './datos-usuario.service';

@Component({
  templateUrl: './datos-usuario-delete-dialog.component.html',
})
export class DatosUsuarioDeleteDialogComponent {
  datosUsuario?: IDatosUsuario;

  constructor(
    protected datosUsuarioService: DatosUsuarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.datosUsuarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('datosUsuarioListModification');
      this.activeModal.close();
    });
  }
}
