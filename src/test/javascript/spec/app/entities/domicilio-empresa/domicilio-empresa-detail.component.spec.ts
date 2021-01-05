import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { DomicilioEmpresaDetailComponent } from 'app/entities/domicilio-empresa/domicilio-empresa-detail.component';
import { DomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';

describe('Component Tests', () => {
  describe('DomicilioEmpresa Management Detail Component', () => {
    let comp: DomicilioEmpresaDetailComponent;
    let fixture: ComponentFixture<DomicilioEmpresaDetailComponent>;
    const route = ({ data: of({ domicilioEmpresa: new DomicilioEmpresa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DomicilioEmpresaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DomicilioEmpresaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DomicilioEmpresaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load domicilioEmpresa on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.domicilioEmpresa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
