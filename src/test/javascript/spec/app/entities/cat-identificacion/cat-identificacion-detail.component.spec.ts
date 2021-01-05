import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatIdentificacionDetailComponent } from 'app/entities/cat-identificacion/cat-identificacion-detail.component';
import { CatIdentificacion } from 'app/shared/model/cat-identificacion.model';

describe('Component Tests', () => {
  describe('CatIdentificacion Management Detail Component', () => {
    let comp: CatIdentificacionDetailComponent;
    let fixture: ComponentFixture<CatIdentificacionDetailComponent>;
    const route = ({ data: of({ catIdentificacion: new CatIdentificacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatIdentificacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatIdentificacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatIdentificacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catIdentificacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catIdentificacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
