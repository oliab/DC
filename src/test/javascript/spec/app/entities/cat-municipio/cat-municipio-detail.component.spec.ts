import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatMunicipioDetailComponent } from 'app/entities/cat-municipio/cat-municipio-detail.component';
import { CatMunicipio } from 'app/shared/model/cat-municipio.model';

describe('Component Tests', () => {
  describe('CatMunicipio Management Detail Component', () => {
    let comp: CatMunicipioDetailComponent;
    let fixture: ComponentFixture<CatMunicipioDetailComponent>;
    const route = ({ data: of({ catMunicipio: new CatMunicipio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatMunicipioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatMunicipioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatMunicipioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catMunicipio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catMunicipio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
