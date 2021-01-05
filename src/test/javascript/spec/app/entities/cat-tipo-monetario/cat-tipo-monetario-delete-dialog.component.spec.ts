import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResaMxWebTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CatTipoMonetarioDeleteDialogComponent } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario-delete-dialog.component';
import { CatTipoMonetarioService } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario.service';

describe('Component Tests', () => {
  describe('CatTipoMonetario Management Delete Component', () => {
    let comp: CatTipoMonetarioDeleteDialogComponent;
    let fixture: ComponentFixture<CatTipoMonetarioDeleteDialogComponent>;
    let service: CatTipoMonetarioService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoMonetarioDeleteDialogComponent],
      })
        .overrideTemplate(CatTipoMonetarioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatTipoMonetarioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoMonetarioService);
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
