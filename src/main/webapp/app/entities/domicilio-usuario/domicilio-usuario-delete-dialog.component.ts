import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';
import { DomicilioUsuarioService } from './domicilio-usuario.service';

@Component({
  templateUrl: './domicilio-usuario-delete-dialog.component.html',
})
export class DomicilioUsuarioDeleteDialogComponent {
  domicilioUsuario?: IDomicilioUsuario;

  constructor(
    protected domicilioUsuarioService: DomicilioUsuarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.domicilioUsuarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('domicilioUsuarioListModification');
      this.activeModal.close();
    });
  }
}
