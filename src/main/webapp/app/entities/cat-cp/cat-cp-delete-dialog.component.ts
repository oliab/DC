import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatCP } from 'app/shared/model/cat-cp.model';
import { CatCPService } from './cat-cp.service';

@Component({
  templateUrl: './cat-cp-delete-dialog.component.html',
})
export class CatCPDeleteDialogComponent {
  catCP?: ICatCP;

  constructor(protected catCPService: CatCPService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catCPService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catCPListModification');
      this.activeModal.close();
    });
  }
}
