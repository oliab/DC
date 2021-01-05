import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';
import { CatTipoEmpresaService } from './cat-tipo-empresa.service';

@Component({
  templateUrl: './cat-tipo-empresa-delete-dialog.component.html',
})
export class CatTipoEmpresaDeleteDialogComponent {
  catTipoEmpresa?: ICatTipoEmpresa;

  constructor(
    protected catTipoEmpresaService: CatTipoEmpresaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catTipoEmpresaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catTipoEmpresaListModification');
      this.activeModal.close();
    });
  }
}
