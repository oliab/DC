import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoEmpresaUpdateComponent } from 'app/entities/cat-tipo-empresa/cat-tipo-empresa-update.component';
import { CatTipoEmpresaService } from 'app/entities/cat-tipo-empresa/cat-tipo-empresa.service';
import { CatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';

describe('Component Tests', () => {
  describe('CatTipoEmpresa Management Update Component', () => {
    let comp: CatTipoEmpresaUpdateComponent;
    let fixture: ComponentFixture<CatTipoEmpresaUpdateComponent>;
    let service: CatTipoEmpresaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoEmpresaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatTipoEmpresaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatTipoEmpresaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoEmpresaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatTipoEmpresa(123);
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
        const entity = new CatTipoEmpresa();
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
