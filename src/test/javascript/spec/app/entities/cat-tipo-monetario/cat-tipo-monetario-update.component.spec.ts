import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoMonetarioUpdateComponent } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario-update.component';
import { CatTipoMonetarioService } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario.service';
import { CatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';

describe('Component Tests', () => {
  describe('CatTipoMonetario Management Update Component', () => {
    let comp: CatTipoMonetarioUpdateComponent;
    let fixture: ComponentFixture<CatTipoMonetarioUpdateComponent>;
    let service: CatTipoMonetarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoMonetarioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatTipoMonetarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatTipoMonetarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoMonetarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatTipoMonetario(123);
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
        const entity = new CatTipoMonetario();
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
