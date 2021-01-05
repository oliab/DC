import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatSucursal } from 'app/shared/model/cat-sucursal.model';
import { CatSucursalService } from './cat-sucursal.service';

@Component({
  templateUrl: './cat-sucursal-delete-dialog.component.html',
})
export class CatSucursalDeleteDialogComponent {
  catSucursal?: ICatSucursal;

  constructor(
    protected catSucursalService: CatSucursalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catSucursalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catSucursalListModification');
      this.activeModal.close();
    });
  }
}
