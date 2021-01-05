import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoOperacionUpdateComponent } from 'app/entities/cat-tipo-operacion/cat-tipo-operacion-update.component';
import { CatTipoOperacionService } from 'app/entities/cat-tipo-operacion/cat-tipo-operacion.service';
import { CatTipoOperacion } from 'app/shared/model/cat-tipo-operacion.model';

describe('Component Tests', () => {
  describe('CatTipoOperacion Management Update Component', () => {
    let comp: CatTipoOperacionUpdateComponent;
    let fixture: ComponentFixture<CatTipoOperacionUpdateComponent>;
    let service: CatTipoOperacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoOperacionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatTipoOperacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatTipoOperacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoOperacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatTipoOperacion(123);
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
        const entity = new CatTipoOperacion();
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
