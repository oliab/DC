import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoEmpresaDetailComponent } from 'app/entities/cat-tipo-empresa/cat-tipo-empresa-detail.component';
import { CatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';

describe('Component Tests', () => {
  describe('CatTipoEmpresa Management Detail Component', () => {
    let comp: CatTipoEmpresaDetailComponent;
    let fixture: ComponentFixture<CatTipoEmpresaDetailComponent>;
    const route = ({ data: of({ catTipoEmpresa: new CatTipoEmpresa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoEmpresaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatTipoEmpresaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatTipoEmpresaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catTipoEmpresa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catTipoEmpresa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
