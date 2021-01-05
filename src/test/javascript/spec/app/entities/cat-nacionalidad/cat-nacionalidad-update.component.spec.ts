import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatNacionalidadUpdateComponent } from 'app/entities/cat-nacionalidad/cat-nacionalidad-update.component';
import { CatNacionalidadService } from 'app/entities/cat-nacionalidad/cat-nacionalidad.service';
import { CatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';

describe('Component Tests', () => {
  describe('CatNacionalidad Management Update Component', () => {
    let comp: CatNacionalidadUpdateComponent;
    let fixture: ComponentFixture<CatNacionalidadUpdateComponent>;
    let service: CatNacionalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatNacionalidadUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatNacionalidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatNacionalidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatNacionalidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatNacionalidad(123);
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
        const entity = new CatNacionalidad();
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
