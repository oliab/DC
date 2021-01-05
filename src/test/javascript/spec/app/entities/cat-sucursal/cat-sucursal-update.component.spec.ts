import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatSucursalUpdateComponent } from 'app/entities/cat-sucursal/cat-sucursal-update.component';
import { CatSucursalService } from 'app/entities/cat-sucursal/cat-sucursal.service';
import { CatSucursal } from 'app/shared/model/cat-sucursal.model';

describe('Component Tests', () => {
  describe('CatSucursal Management Update Component', () => {
    let comp: CatSucursalUpdateComponent;
    let fixture: ComponentFixture<CatSucursalUpdateComponent>;
    let service: CatSucursalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatSucursalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatSucursalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatSucursalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatSucursalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatSucursal(123);
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
        const entity = new CatSucursal();
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
