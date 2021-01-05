import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatIdentificacionUpdateComponent } from 'app/entities/cat-identificacion/cat-identificacion-update.component';
import { CatIdentificacionService } from 'app/entities/cat-identificacion/cat-identificacion.service';
import { CatIdentificacion } from 'app/shared/model/cat-identificacion.model';

describe('Component Tests', () => {
  describe('CatIdentificacion Management Update Component', () => {
    let comp: CatIdentificacionUpdateComponent;
    let fixture: ComponentFixture<CatIdentificacionUpdateComponent>;
    let service: CatIdentificacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatIdentificacionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatIdentificacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatIdentificacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatIdentificacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatIdentificacion(123);
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
        const entity = new CatIdentificacion();
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
