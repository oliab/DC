import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DatosUsuarioService } from 'app/entities/datos-usuario/datos-usuario.service';
import { IDatosUsuario, DatosUsuario } from 'app/shared/model/datos-usuario.model';

describe('Service Tests', () => {
  describe('DatosUsuario Service', () => {
    let injector: TestBed;
    let service: DatosUsuarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IDatosUsuario;
    let expectedResult: IDatosUsuario | IDatosUsuario[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DatosUsuarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DatosUsuario(0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DatosUsuario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAct: currentDate,
          },
          returnedFromService
        );

        service.create(new DatosUsuario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DatosUsuario', () => {
        const returnedFromService = Object.assign(
          {
            puesto: 'BBBBBB',
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAct: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DatosUsuario', () => {
        const returnedFromService = Object.assign(
          {
            puesto: 'BBBBBB',
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAct: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DatosUsuario', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
