import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ResaMxWebTestModule } from '../../../test.module';
import { DomicilioUsuarioComponent } from 'app/entities/domicilio-usuario/domicilio-usuario.component';
import { DomicilioUsuarioService } from 'app/entities/domicilio-usuario/domicilio-usuario.service';
import { DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

describe('Component Tests', () => {
  describe('DomicilioUsuario Management Component', () => {
    let comp: DomicilioUsuarioComponent;
    let fixture: ComponentFixture<DomicilioUsuarioComponent>;
    let service: DomicilioUsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DomicilioUsuarioComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(DomicilioUsuarioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DomicilioUsuarioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DomicilioUsuarioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DomicilioUsuario(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.domicilioUsuarios && comp.domicilioUsuarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DomicilioUsuario(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.domicilioUsuarios && comp.domicilioUsuarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
