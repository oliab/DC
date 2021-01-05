import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResaMxWebTestModule } from '../../../test.module';
import { DomicilioUsuarioDetailComponent } from 'app/entities/domicilio-usuario/domicilio-usuario-detail.component';
import { DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

describe('Component Tests', () => {
  describe('DomicilioUsuario Management Detail Component', () => {
    let comp: DomicilioUsuarioDetailComponent;
    let fixture: ComponentFixture<DomicilioUsuarioDetailComponent>;
    const route = ({ data: of({ domicilioUsuario: new DomicilioUsuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResaMxWebTestModule],
        declarations: [DomicilioUsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DomicilioUsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DomicilioUsuarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load domicilioUsuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.domicilioUsuario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
