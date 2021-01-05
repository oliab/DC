import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatCPDetailComponent } from 'app/entities/cat-cp/cat-cp-detail.component';
import { CatCP } from 'app/shared/model/cat-cp.model';

describe('Component Tests', () => {
  describe('CatCP Management Detail Component', () => {
    let comp: CatCPDetailComponent;
    let fixture: ComponentFixture<CatCPDetailComponent>;
    const route = ({ data: of({ catCP: new CatCP(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatCPDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatCPDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatCPDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catCP on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catCP).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
