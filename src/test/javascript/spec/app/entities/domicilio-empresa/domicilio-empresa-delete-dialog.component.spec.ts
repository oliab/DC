import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResaMxWebTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { DomicilioEmpresaDeleteDialogComponent } from 'app/entities/domicilio-empresa/domicilio-empresa-delete-dialog.component';
import { DomicilioEmpresaService } from 'app/entities/domicilio-empresa/domicilio-empresa.service';

describe('Component Tests', () => {
  describe('DomicilioEmpresa Management Delete Component', () => {
    let comp: DomicilioEmpresaDeleteDialogComponent;
    let fixture: ComponentFixture<DomicilioEmpresaDeleteDialogComponent>;
    let service: DomicilioEmpresaService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DomicilioEmpresaDeleteDialogComponent],
      })
        .overrideTemplate(DomicilioEmpresaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DomicilioEmpresaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DomicilioEmpresaService);
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
