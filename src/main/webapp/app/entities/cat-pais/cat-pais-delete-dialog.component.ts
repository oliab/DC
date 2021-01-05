import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatPais } from 'app/shared/model/cat-pais.model';
import { CatPaisService } from './cat-pais.service';

@Component({
  templateUrl: './cat-pais-delete-dialog.component.html',
})
export class CatPaisDeleteDialogComponent {
  catPais?: ICatPais;

  constructor(protected catPaisService: CatPaisService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.catPaisService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catPaisListModification');
      this.activeModal.close();
    });
  }
}
