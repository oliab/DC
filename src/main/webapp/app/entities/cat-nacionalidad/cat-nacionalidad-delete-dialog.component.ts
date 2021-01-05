import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';
import { CatNacionalidadService } from './cat-nacionalidad.service';

@Component({
  templateUrl: './cat-nacionalidad-delete-dialog.component.html',
})
export class CatNacionalidadDeleteDialogComponent {
  catNacionalidad?: ICatNacionalidad;

  constructor(
    protected catNacionalidadService: CatNacionalidadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catNacionalidadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catNacionalidadListModification');
      this.activeModal.close();
    });
  }
}
