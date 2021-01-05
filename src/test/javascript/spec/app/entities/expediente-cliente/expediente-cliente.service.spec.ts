import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ExpedienteClienteService } from 'app/entities/expediente-cliente/expediente-cliente.service';
import { IExpedienteCliente, ExpedienteCliente } from 'app/shared/model/expediente-cliente.model';

describe('Service Tests', () => {
  describe('ExpedienteCliente Service', () => {
    let injector: TestBed;
    let service: ExpedienteClienteService;
    let httpMock: HttpTestingController;
    let elemDefault: IExpedienteCliente;
    let expectedResult: IExpedienteCliente | IExpedienteCliente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ExpedienteClienteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ExpedienteCliente(0, false, 'AAAAAAA', 'image/png', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaAlta: currentDate.format(DATE_FORMAT),
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ExpedienteCliente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaAlta: currentDate.format(DATE_FORMAT),
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlta: currentDate,
            fechaAct: currentDate,
          },
          returnedFromService
        );

        service.create(new ExpedienteCliente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ExpedienteCliente', () => {
        const returnedFromService = Object.assign(
          {
            empresarial: true,
            descripcion: 'BBBBBB',
            documento: 'BBBBBB',
            fechaAlta: currentDate.format(DATE_FORMAT),
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlta: currentDate,
            fechaAct: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ExpedienteCliente', () => {
        const returnedFromService = Object.assign(
          {
            empresarial: true,
            descripcion: 'BBBBBB',
            documento: 'BBBBBB',
            fechaAlta: currentDate.format(DATE_FORMAT),
            fechaAct: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlta: currentDate,
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

      it('should delete a ExpedienteCliente', () => {
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
