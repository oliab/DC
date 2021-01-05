import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatEstadoUpdateComponent } from 'app/entities/cat-estado/cat-estado-update.component';
import { CatEstadoService } from 'app/entities/cat-estado/cat-estado.service';
import { CatEstado } from 'app/shared/model/cat-estado.model';

describe('Component Tests', () => {
  describe('CatEstado Management Update Component', () => {
    let comp: CatEstadoUpdateComponent;
    let fixture: ComponentFixture<CatEstadoUpdateComponent>;
    let service: CatEstadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatEstadoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatEstadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatEstadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatEstadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatEstado(123);
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
        const entity = new CatEstado();
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
