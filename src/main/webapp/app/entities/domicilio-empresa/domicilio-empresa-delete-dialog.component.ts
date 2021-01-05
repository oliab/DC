import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';
import { DomicilioEmpresaService } from './domicilio-empresa.service';

@Component({
  templateUrl: './domicilio-empresa-delete-dialog.component.html',
})
export class DomicilioEmpresaDeleteDialogComponent {
  domicilioEmpresa?: IDomicilioEmpresa;

  constructor(
    protected domicilioEmpresaService: DomicilioEmpresaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.domicilioEmpresaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('domicilioEmpresaListModification');
      this.activeModal.close();
    });
  }
}
