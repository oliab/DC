import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatPaisDetailComponent } from 'app/entities/cat-pais/cat-pais-detail.component';
import { CatPais } from 'app/shared/model/cat-pais.model';

describe('Component Tests', () => {
  describe('CatPais Management Detail Component', () => {
    let comp: CatPaisDetailComponent;
    let fixture: ComponentFixture<CatPaisDetailComponent>;
    const route = ({ data: of({ catPais: new CatPais(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatPaisDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatPaisDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatPaisDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catPais on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catPais).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
