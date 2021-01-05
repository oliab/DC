import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatSectorDetailComponent } from 'app/entities/cat-sector/cat-sector-detail.component';
import { CatSector } from 'app/shared/model/cat-sector.model';

describe('Component Tests', () => {
  describe('CatSector Management Detail Component', () => {
    let comp: CatSectorDetailComponent;
    let fixture: ComponentFixture<CatSectorDetailComponent>;
    const route = ({ data: of({ catSector: new CatSector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatSectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatSectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatSectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catSector on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catSector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
