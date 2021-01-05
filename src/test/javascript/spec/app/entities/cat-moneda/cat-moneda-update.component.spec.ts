import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatMonedaUpdateComponent } from 'app/entities/cat-moneda/cat-moneda-update.component';
import { CatMonedaService } from 'app/entities/cat-moneda/cat-moneda.service';
import { CatMoneda } from 'app/shared/model/cat-moneda.model';

describe('Component Tests', () => {
  describe('CatMoneda Management Update Component', () => {
    let comp: CatMonedaUpdateComponent;
    let fixture: ComponentFixture<CatMonedaUpdateComponent>;
    let service: CatMonedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatMonedaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatMonedaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatMonedaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatMonedaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatMoneda(123);
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
        const entity = new CatMoneda();
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
