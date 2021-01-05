import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoDocumentoUpdateComponent } from 'app/entities/cat-tipo-documento/cat-tipo-documento-update.component';
import { CatTipoDocumentoService } from 'app/entities/cat-tipo-documento/cat-tipo-documento.service';
import { CatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';

describe('Component Tests', () => {
  describe('CatTipoDocumento Management Update Component', () => {
    let comp: CatTipoDocumentoUpdateComponent;
    let fixture: ComponentFixture<CatTipoDocumentoUpdateComponent>;
    let service: CatTipoDocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoDocumentoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatTipoDocumentoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatTipoDocumentoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoDocumentoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatTipoDocumento(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatTipoDocumento();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
