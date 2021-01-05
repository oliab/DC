import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoMonetarioDetailComponent } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario-detail.component';
import { CatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';

describe('Component Tests', () => {
  describe('CatTipoMonetario Management Detail Component', () => {
    let comp: CatTipoMonetarioDetailComponent;
    let fixture: ComponentFixture<CatTipoMonetarioDetailComponent>;
    const route = ({ data: of({ catTipoMonetario: new CatTipoMonetario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoMonetarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatTipoMonetarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatTipoMonetarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catTipoMonetario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catTipoMonetario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
