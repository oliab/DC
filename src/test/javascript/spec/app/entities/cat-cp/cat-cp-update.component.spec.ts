import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatCPUpdateComponent } from 'app/entities/cat-cp/cat-cp-update.component';
import { CatCPService } from 'app/entities/cat-cp/cat-cp.service';
import { CatCP } from 'app/shared/model/cat-cp.model';

describe('Component Tests', () => {
  describe('CatCP Management Update Component', () => {
    let comp: CatCPUpdateComponent;
    let fixture: ComponentFixture<CatCPUpdateComponent>;
    let service: CatCPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatCPUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatCPUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatCPUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatCPService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatCP(123);
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
        const entity = new CatCP();
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
