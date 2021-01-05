import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DomicilioUsuarioService } from 'app/entities/domicilio-usuario/domicilio-usuario.service';
import { IDomicilioUsuario, DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

describe('Service Tests', () => {
  describe('DomicilioUsuario Service', () => {
    let injector: TestBed;
    let service: DomicilioUsuarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IDomicilioUsuario;
    let expectedResult: IDomicilioUsuario | IDomicilioUsuario[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DomicilioUsuarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DomicilioUsuario(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
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

      it('should create a DomicilioUsuario', () => {
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

        service.create(new DomicilioUsuario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DomicilioUsuario', () => {
        const returnedFromService = Object.assign(
          {
            colonia: 'BBBBBB',
            calle: 'BBBBBB',
            noExt: 'BBBBBB',
            noInt: 'BBBBBB',
            domicilio: 'BBBBBB',
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

      it('should return a list of DomicilioUsuario', () => {
        const returnedFromService = Object.assign(
          {
            colonia: 'BBBBBB',
            calle: 'BBBBBB',
            noExt: 'BBBBBB',
            noInt: 'BBBBBB',
            domicilio: 'BBBBBB',
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

      it('should delete a DomicilioUsuario', () => {
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
