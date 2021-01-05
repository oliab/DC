import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { DatosUsuarioDetailComponent } from 'app/entities/datos-usuario/datos-usuario-detail.component';
import { DatosUsuario } from 'app/shared/model/datos-usuario.model';

describe('Component Tests', () => {
  describe('DatosUsuario Management Detail Component', () => {
    let comp: DatosUsuarioDetailComponent;
    let fixture: ComponentFixture<DatosUsuarioDetailComponent>;
    const route = ({ data: of({ datosUsuario: new DatosUsuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DatosUsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DatosUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DatosUsuarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load datosUsuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.datosUsuario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
