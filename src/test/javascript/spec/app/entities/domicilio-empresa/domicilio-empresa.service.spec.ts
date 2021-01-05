import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DomicilioEmpresaService } from 'app/entities/domicilio-empresa/domicilio-empresa.service';
import { IDomicilioEmpresa, DomicilioEmpresa } from 'app/shared/model/domicilio-empresa.model';

describe('Service Tests', () => {
  describe('DomicilioEmpresa Service', () => {
    let injector: TestBed;
    let service: DomicilioEmpresaService;
    let httpMock: HttpTestingController;
    let elemDefault: IDomicilioEmpresa;
    let expectedResult: IDomicilioEmpresa | IDomicilioEmpresa[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DomicilioEmpresaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DomicilioEmpresa(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
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

      it('should create a DomicilioEmpresa', () => {
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

        service.create(new DomicilioEmpresa()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DomicilioEmpresa', () => {
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

      it('should return a list of DomicilioEmpresa', () => {
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

      it('should delete a DomicilioEmpresa', () => {
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
