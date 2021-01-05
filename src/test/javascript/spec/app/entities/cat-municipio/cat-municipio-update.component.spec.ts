import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatMunicipioUpdateComponent } from 'app/entities/cat-municipio/cat-municipio-update.component';
import { CatMunicipioService } from 'app/entities/cat-municipio/cat-municipio.service';
import { CatMunicipio } from 'app/shared/model/cat-municipio.model';

describe('Component Tests', () => {
  describe('CatMunicipio Management Update Component', () => {
    let comp: CatMunicipioUpdateComponent;
    let fixture: ComponentFixture<CatMunicipioUpdateComponent>;
    let service: CatMunicipioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatMunicipioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatMunicipioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatMunicipioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatMunicipioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatMunicipio(123);
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
        const entity = new CatMunicipio();
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
