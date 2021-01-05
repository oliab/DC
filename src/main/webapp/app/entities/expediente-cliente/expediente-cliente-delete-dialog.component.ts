import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpedienteCliente } from 'app/shared/model/expediente-cliente.model';
import { ExpedienteClienteService } from './expediente-cliente.service';

@Component({
  templateUrl: './expediente-cliente-delete-dialog.component.html',
})
export class ExpedienteClienteDeleteDialogComponent {
  expedienteCliente?: IExpedienteCliente;

  constructor(
    protected expedienteClienteService: ExpedienteClienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expedienteClienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expedienteClienteListModification');
      this.activeModal.close();
    });
  }
}
