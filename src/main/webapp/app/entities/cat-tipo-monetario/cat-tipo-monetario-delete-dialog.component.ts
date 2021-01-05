import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';
import { CatTipoMonetarioService } from './cat-tipo-monetario.service';

@Component({
  templateUrl: './cat-tipo-monetario-delete-dialog.component.html',
})
export class CatTipoMonetarioDeleteDialogComponent {
  catTipoMonetario?: ICatTipoMonetario;

  constructor(
    protected catTipoMonetarioService: CatTipoMonetarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catTipoMonetarioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catTipoMonetarioListModification');
      this.activeModal.close();
    });
  }
}
