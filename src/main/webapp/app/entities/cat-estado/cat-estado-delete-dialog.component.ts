import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatEstado } from 'app/shared/model/cat-estado.model';
import { CatEstadoService } from './cat-estado.service';

@Component({
  templateUrl: './cat-estado-delete-dialog.component.html',
})
export class CatEstadoDeleteDialogComponent {
  catEstado?: ICatEstado;

  constructor(protected catEstadoService: CatEstadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catEstadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catEstadoListModification');
      this.activeModal.close();
    });
  }
}
