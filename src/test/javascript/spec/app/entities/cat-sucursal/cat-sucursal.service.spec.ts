import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CatSucursalService } from 'app/entities/cat-sucursal/cat-sucursal.service';
import { ICatSucursal, CatSucursal } from 'app/shared/model/cat-sucursal.model';

describe('Service Tests', () => {
  describe('CatSucursal Service', () => {
    let injector: TestBed;
    let service: CatSucursalService;
    let httpMock: HttpTestingController;
    let elemDefault: ICatSucursal;
    let expectedResult: ICatSucursal | ICatSucursal[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CatSucursalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CatSucursal(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
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

      it('should create a CatSucursal', () => {
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

        service.create(new CatSucursal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CatSucursal', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            direccion: 'BBBBBB',
            telefono: 'BBBBBB',
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

      it('should return a list of CatSucursal', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            direccion: 'BBBBBB',
            telefono: 'BBBBBB',
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

      it('should delete a CatSucursal', () => {
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
