import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatTipoOperacion } from 'app/shared/model/cat-tipo-operacion.model';
import { CatTipoOperacionService } from './cat-tipo-operacion.service';

@Component({
  templateUrl: './cat-tipo-operacion-delete-dialog.component.html',
})
export class CatTipoOperacionDeleteDialogComponent {
  catTipoOperacion?: ICatTipoOperacion;

  constructor(
    protected catTipoOperacionService: CatTipoOperacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catTipoOperacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catTipoOperacionListModification');
      this.activeModal.close();
    });
  }
}
