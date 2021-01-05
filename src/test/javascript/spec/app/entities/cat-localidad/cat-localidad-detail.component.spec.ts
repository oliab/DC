import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatLocalidadDetailComponent } from 'app/entities/cat-localidad/cat-localidad-detail.component';
import { CatLocalidad } from 'app/shared/model/cat-localidad.model';

describe('Component Tests', () => {
  describe('CatLocalidad Management Detail Component', () => {
    let comp: CatLocalidadDetailComponent;
    let fixture: ComponentFixture<CatLocalidadDetailComponent>;
    const route = ({ data: of({ catLocalidad: new CatLocalidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatLocalidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatLocalidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatLocalidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catLocalidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catLocalidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
