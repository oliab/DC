import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { CatIdentificacionService } from './cat-identificacion.service';

@Component({
  templateUrl: './cat-identificacion-delete-dialog.component.html',
})
export class CatIdentificacionDeleteDialogComponent {
  catIdentificacion?: ICatIdentificacion;

  constructor(
    protected catIdentificacionService: CatIdentificacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catIdentificacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catIdentificacionListModification');
      this.activeModal.close();
    });
  }
}
