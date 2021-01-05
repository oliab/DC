import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatSucursalDetailComponent } from 'app/entities/cat-sucursal/cat-sucursal-detail.component';
import { CatSucursal } from 'app/shared/model/cat-sucursal.model';

describe('Component Tests', () => {
  describe('CatSucursal Management Detail Component', () => {
    let comp: CatSucursalDetailComponent;
    let fixture: ComponentFixture<CatSucursalDetailComponent>;
    const route = ({ data: of({ catSucursal: new CatSucursal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatSucursalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatSucursalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatSucursalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catSucursal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catSucursal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
