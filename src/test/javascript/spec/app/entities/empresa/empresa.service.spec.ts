import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';

describe('Service Tests', () => {
  describe('Empresa Service', () => {
    let injector: TestBed;
    let service: EmpresaService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmpresa;
    let expectedResult: IEmpresa | IEmpresa[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmpresaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Empresa(0, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
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

      it('should create a Empresa', () => {
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

        service.create(new Empresa()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Empresa', () => {
        const returnedFromService = Object.assign(
          {
            fideicomiso: true,
            rfc: 'BBBBBB',
            razonSocial: 'BBBBBB',
            noIdentificacion: 'BBBBBB',
            telefono: 'BBBBBB',
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

      it('should return a list of Empresa', () => {
        const returnedFromService = Object.assign(
          {
            fideicomiso: true,
            rfc: 'BBBBBB',
            razonSocial: 'BBBBBB',
            noIdentificacion: 'BBBBBB',
            telefono: 'BBBBBB',
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

      it('should delete a Empresa', () => {
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
