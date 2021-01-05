import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ResaMxWebTestModule } from '../../../test.module';
import { ExpedienteClienteDetailComponent } from 'app/entities/expediente-cliente/expediente-cliente-detail.component';
import { ExpedienteCliente } from 'app/shared/model/expediente-cliente.model';

describe('Component Tests', () => {
  describe('ExpedienteCliente Management Detail Component', () => {
    let comp: ExpedienteClienteDetailComponent;
    let fixture: ComponentFixture<ExpedienteClienteDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ expedienteCliente: new ExpedienteCliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [ExpedienteClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExpedienteClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpedienteClienteDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load expedienteCliente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expedienteCliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
