import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { DomicilioUsuarioUpdateComponent } from 'app/entities/domicilio-usuario/domicilio-usuario-update.component';
import { DomicilioUsuarioService } from 'app/entities/domicilio-usuario/domicilio-usuario.service';
import { DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

describe('Component Tests', () => {
  describe('DomicilioUsuario Management Update Component', () => {
    let comp: DomicilioUsuarioUpdateComponent;
    let fixture: ComponentFixture<DomicilioUsuarioUpdateComponent>;
    let service: DomicilioUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DomicilioUsuarioUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DomicilioUsuarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DomicilioUsuarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DomicilioUsuarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DomicilioUsuario(123);
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
        const entity = new DomicilioUsuario();
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
