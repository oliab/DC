import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResaMxWebTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CatTipoDocumentoDeleteDialogComponent } from 'app/entities/cat-tipo-documento/cat-tipo-documento-delete-dialog.component';
import { CatTipoDocumentoService } from 'app/entities/cat-tipo-documento/cat-tipo-documento.service';

describe('Component Tests', () => {
  describe('CatTipoDocumento Management Delete Component', () => {
    let comp: CatTipoDocumentoDeleteDialogComponent;
    let fixture: ComponentFixture<CatTipoDocumentoDeleteDialogComponent>;
    let service: CatTipoDocumentoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoDocumentoDeleteDialogComponent],
      })
        .overrideTemplate(CatTipoDocumentoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatTipoDocumentoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoDocumentoService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
