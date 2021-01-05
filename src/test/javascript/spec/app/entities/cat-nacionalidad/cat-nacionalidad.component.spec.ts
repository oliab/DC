import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatNacionalidadComponent } from 'app/entities/cat-nacionalidad/cat-nacionalidad.component';
import { CatNacionalidadService } from 'app/entities/cat-nacionalidad/cat-nacionalidad.service';
import { CatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';

describe('Component Tests', () => {
  describe('CatNacionalidad Management Component', () => {
    let comp: CatNacionalidadComponent;
    let fixture: ComponentFixture<CatNacionalidadComponent>;
    let service: CatNacionalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatNacionalidadComponent],
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
        .overrideTemplate(CatNacionalidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatNacionalidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatNacionalidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CatNacionalidad(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.catNacionalidads && comp.catNacionalidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CatNacionalidad(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.catNacionalidads && comp.catNacionalidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
