import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { DomicilioEmpresaUpdateComponent } from 'app/entities/domicilio-empresa/domicilio-empresa-update.component';
import { DomicilioEmpresaService } from 'app/entities/domicilio-empresa/domicilio-empresa.service';
import { DomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';

describe('Component Tests', () => {
  describe('DomicilioEmpresa Management Update Component', () => {
    let comp: DomicilioEmpresaUpdateComponent;
    let fixture: ComponentFixture<DomicilioEmpresaUpdateComponent>;
    let service: DomicilioEmpresaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DomicilioEmpresaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DomicilioEmpresaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DomicilioEmpresaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DomicilioEmpresaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DomicilioEmpresa(123);
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
        const entity = new DomicilioEmpresa();
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
