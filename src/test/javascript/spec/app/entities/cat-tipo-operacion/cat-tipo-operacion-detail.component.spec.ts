import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoOperacionDetailComponent } from 'app/entities/cat-tipo-operacion/cat-tipo-operacion-detail.component';
import { CatTipoOperacion } from 'app/shared/model/cat-tipo-operacion.model';

describe('Component Tests', () => {
  describe('CatTipoOperacion Management Detail Component', () => {
    let comp: CatTipoOperacionDetailComponent;
    let fixture: ComponentFixture<CatTipoOperacionDetailComponent>;
    const route = ({ data: of({ catTipoOperacion: new CatTipoOperacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoOperacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatTipoOperacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatTipoOperacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catTipoOperacion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catTipoOperacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
