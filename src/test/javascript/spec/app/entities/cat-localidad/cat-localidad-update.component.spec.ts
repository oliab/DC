import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatLocalidadUpdateComponent } from 'app/entities/cat-localidad/cat-localidad-update.component';
import { CatLocalidadService } from 'app/entities/cat-localidad/cat-localidad.service';
import { CatLocalidad } from 'app/shared/model/cat-localidad.model';

describe('Component Tests', () => {
  describe('CatLocalidad Management Update Component', () => {
    let comp: CatLocalidadUpdateComponent;
    let fixture: ComponentFixture<CatLocalidadUpdateComponent>;
    let service: CatLocalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatLocalidadUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatLocalidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatLocalidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatLocalidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatLocalidad(123);
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
        const entity = new CatLocalidad();
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
