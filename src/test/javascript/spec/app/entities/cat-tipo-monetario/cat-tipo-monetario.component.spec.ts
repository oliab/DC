import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoMonetarioComponent } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario.component';
import { CatTipoMonetarioService } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario.service';
import { CatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';

describe('Component Tests', () => {
  describe('CatTipoMonetario Management Component', () => {
    let comp: CatTipoMonetarioComponent;
    let fixture: ComponentFixture<CatTipoMonetarioComponent>;
    let service: CatTipoMonetarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoMonetarioComponent],
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
        .overrideTemplate(CatTipoMonetarioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatTipoMonetarioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatTipoMonetarioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CatTipoMonetario(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.catTipoMonetarios && comp.catTipoMonetarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CatTipoMonetario(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.catTipoMonetarios && comp.catTipoMonetarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
