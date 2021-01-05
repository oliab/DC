import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatEstadoDetailComponent } from 'app/entities/cat-estado/cat-estado-detail.component';
import { CatEstado } from 'app/shared/model/cat-estado.model';

describe('Component Tests', () => {
  describe('CatEstado Management Detail Component', () => {
    let comp: CatEstadoDetailComponent;
    let fixture: ComponentFixture<CatEstadoDetailComponent>;
    const route = ({ data: of({ catEstado: new CatEstado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatEstadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatEstadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatEstadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catEstado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catEstado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
