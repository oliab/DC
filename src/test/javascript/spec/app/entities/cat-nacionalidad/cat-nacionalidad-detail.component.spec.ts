import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatNacionalidadDetailComponent } from 'app/entities/cat-nacionalidad/cat-nacionalidad-detail.component';
import { CatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';

describe('Component Tests', () => {
  describe('CatNacionalidad Management Detail Component', () => {
    let comp: CatNacionalidadDetailComponent;
    let fixture: ComponentFixture<CatNacionalidadDetailComponent>;
    const route = ({ data: of({ catNacionalidad: new CatNacionalidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatNacionalidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatNacionalidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatNacionalidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catNacionalidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catNacionalidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
