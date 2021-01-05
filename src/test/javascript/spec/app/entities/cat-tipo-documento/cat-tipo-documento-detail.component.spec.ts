import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { CatTipoDocumentoDetailComponent } from 'app/entities/cat-tipo-documento/cat-tipo-documento-detail.component';
import { CatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';

describe('Component Tests', () => {
  describe('CatTipoDocumento Management Detail Component', () => {
    let comp: CatTipoDocumentoDetailComponent;
    let fixture: ComponentFixture<CatTipoDocumentoDetailComponent>;
    const route = ({ data: of({ catTipoDocumento: new CatTipoDocumento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [CatTipoDocumentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CatTipoDocumentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CatTipoDocumentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load catTipoDocumento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.catTipoDocumento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
