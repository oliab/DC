import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';
import { CatTipoDocumentoService } from './cat-tipo-documento.service';

@Component({
  templateUrl: './cat-tipo-documento-delete-dialog.component.html',
})
export class CatTipoDocumentoDeleteDialogComponent {
  catTipoDocumento?: ICatTipoDocumento;

  constructor(
    protected catTipoDocumentoService: CatTipoDocumentoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catTipoDocumentoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catTipoDocumentoListModification');
      this.activeModal.close();
    });
  }
}
