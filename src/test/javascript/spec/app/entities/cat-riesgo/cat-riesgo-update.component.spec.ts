import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatRiesgoUpdateComponent } from 'app/entities/cat-riesgo/cat-riesgo-update.component';
import { CatRiesgoService } from 'app/entities/cat-riesgo/cat-riesgo.service';
import { CatRiesgo } from 'app/shared/model/cat-riesgo.model';

describe('Component Tests', () => {
  describe('CatRiesgo Management Update Component', () => {
    let comp: CatRiesgoUpdateComponent;
    let fixture: ComponentFixture<CatRiesgoUpdateComponent>;
    let service: CatRiesgoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatRiesgoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatRiesgoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatRiesgoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatRiesgoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatRiesgo(123);
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
        const entity = new CatRiesgo();
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
