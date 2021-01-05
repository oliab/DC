import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatLocalidad } from 'app/shared/model/cat-localidad.model';
import { CatLocalidadService } from './cat-localidad.service';

@Component({
  templateUrl: './cat-localidad-delete-dialog.component.html',
})
export class CatLocalidadDeleteDialogComponent {
  catLocalidad?: ICatLocalidad;

  constructor(
    protected catLocalidadService: CatLocalidadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catLocalidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catLocalidadListModification');
      this.activeModal.close();
    });
  }
}
