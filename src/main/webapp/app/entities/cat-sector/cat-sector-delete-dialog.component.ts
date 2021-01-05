import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatSector } from 'app/shared/model/cat-sector.model';
import { CatSectorService } from './cat-sector.service';

@Component({
  templateUrl: './cat-sector-delete-dialog.component.html',
})
export class CatSectorDeleteDialogComponent {
  catSector?: ICatSector;

  constructor(protected catSectorService: CatSectorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catSectorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catSectorListModification');
      this.activeModal.close();
    });
  }
}
