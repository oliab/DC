import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatRiesgoDetailComponent } from 'app/entities/cat-riesgo/cat-riesgo-detail.component';
import { CatRiesgo } from 'app/shared/model/cat-riesgo.model';

describe('Component Tests', () => {
  describe('CatRiesgo Management Detail Component', () => {
    let comp: CatRiesgoDetailComponent;
    let fixture: ComponentFixture<CatRiesgoDetailComponent>;
    const route = ({ data: of({ catRiesgo: new CatRiesgo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatRiesgoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatRiesgoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatRiesgoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catRiesgo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catRiesgo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
