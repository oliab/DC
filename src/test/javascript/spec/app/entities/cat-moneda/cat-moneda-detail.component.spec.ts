import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatMonedaDetailComponent } from 'app/entities/cat-moneda/cat-moneda-detail.component';
import { CatMoneda } from 'app/shared/model/cat-moneda.model';

describe('Component Tests', () => {
  describe('CatMoneda Management Detail Component', () => {
    let comp: CatMonedaDetailComponent;
    let fixture: ComponentFixture<CatMonedaDetailComponent>;
    const route = ({ data: of({ catMoneda: new CatMoneda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatMonedaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatMonedaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatMonedaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catMoneda on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catMoneda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
