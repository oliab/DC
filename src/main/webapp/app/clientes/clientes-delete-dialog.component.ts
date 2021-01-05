import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';
import { ClientesService } from './clientes.service';

@Component({
  templateUrl: './clientes-delete-dialog.component.html',
})
export class ClientesDeleteDialogComponent {
  cliente?: ICliente;

  constructor(protected clienteService: ClientesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.clienteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('clienteListModification');
      this.activeModal.close();
    });
  }
}
