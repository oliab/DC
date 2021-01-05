import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { ExpedienteClienteUpdateComponent } from 'app/entities/expediente-cliente/expediente-cliente-update.component';
import { ExpedienteClienteService } from 'app/entities/expediente-cliente/expediente-cliente.service';
import { ExpedienteCliente } from 'app/shared/model/expediente-cliente.model';

describe('Component Tests', () => {
  describe('ExpedienteCliente Management Update Component', () => {
    let comp: ExpedienteClienteUpdateComponent;
    let fixture: ComponentFixture<ExpedienteClienteUpdateComponent>;
    let service: ExpedienteClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [ExpedienteClienteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExpedienteClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpedienteClienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpedienteClienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpedienteCliente(123);
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
        const entity = new ExpedienteCliente();
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
