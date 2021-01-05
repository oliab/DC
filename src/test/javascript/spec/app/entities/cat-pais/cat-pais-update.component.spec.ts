import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatPaisUpdateComponent } from 'app/entities/cat-pais/cat-pais-update.component';
import { CatPaisService } from 'app/entities/cat-pais/cat-pais.service';
import { CatPais } from 'app/shared/model/cat-pais.model';

describe('Component Tests', () => {
  describe('CatPais Management Update Component', () => {
    let comp: CatPaisUpdateComponent;
    let fixture: ComponentFixture<CatPaisUpdateComponent>;
    let service: CatPaisService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatPaisUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatPaisUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatPaisUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatPaisService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatPais(123);
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
        const entity = new CatPais();
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
