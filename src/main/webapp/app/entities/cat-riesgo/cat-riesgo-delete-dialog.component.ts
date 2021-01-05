import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatRiesgo } from 'app/shared/model/cat-riesgo.model';
import { CatRiesgoService } from './cat-riesgo.service';

@Component({
  templateUrl: './cat-riesgo-delete-dialog.component.html',
})
export class CatRiesgoDeleteDialogComponent {
  catRiesgo?: ICatRiesgo;

  constructor(protected catRiesgoService: CatRiesgoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catRiesgoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catRiesgoListModification');
      this.activeModal.close();
    });
  }
}
