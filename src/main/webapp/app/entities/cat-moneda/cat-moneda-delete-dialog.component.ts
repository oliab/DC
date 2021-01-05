import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatMoneda } from 'app/shared/model/cat-moneda.model';
import { CatMonedaService } from './cat-moneda.service';

@Component({
  templateUrl: './cat-moneda-delete-dialog.component.html',
})
export class CatMonedaDeleteDialogComponent {
  catMoneda?: ICatMoneda;

  constructor(protected catMonedaService: CatMonedaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catMonedaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catMonedaListModification');
      this.activeModal.close();
    });
  }
}
