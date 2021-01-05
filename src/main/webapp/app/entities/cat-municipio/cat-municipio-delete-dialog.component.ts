import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';
import { CatMunicipioService } from './cat-municipio.service';

@Component({
  templateUrl: './cat-municipio-delete-dialog.component.html',
})
export class CatMunicipioDeleteDialogComponent {
  catMunicipio?: ICatMunicipio;

  constructor(
    protected catMunicipioService: CatMunicipioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.catMunicipioService.delete(id).subscribe(() => {
      this.eventManager.broadcast('catMunicipioListModification');
      this.activeModal.close();
    });
  }
}
