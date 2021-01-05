import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatSectorUpdateComponent } from 'app/entities/cat-sector/cat-sector-update.component';
import { CatSectorService } from 'app/entities/cat-sector/cat-sector.service';
import { CatSector } from 'app/shared/model/cat-sector.model';

describe('Component Tests', () => {
  describe('CatSector Management Update Component', () => {
    let comp: CatSectorUpdateComponent;
    let fixture: ComponentFixture<CatSectorUpdateComponent>;
    let service: CatSectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatSectorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatSectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatSectorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatSectorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatSector(123);
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
        const entity = new CatSector();
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
