import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CatTipoMonetarioService } from 'app/entities/cat-tipo-monetario/cat-tipo-monetario.service';
import { ICatTipoMonetario, CatTipoMonetario } from 'app/shared/model/cat-tipo-monetario.model';

describe('Service Tests', () => {
  describe('CatTipoMonetario Service', () => {
    let injector: TestBed;
    let service: CatTipoMonetarioService;
    let httpMock: HttpTestingController;
    let elemDefault: ICatTipoMonetario;
    let expectedResult: ICatTipoMonetario | ICatTipoMonetario[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CatTipoMonetarioService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CatTipoMonetario(0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CatTipoMonetario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CatTipoMonetario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CatTipoMonetario', () => {
        const returnedFromService = Object.assign(
          {
            instrumento: 'BBBBBB',
            descripcion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CatTipoMonetario', () => {
        const returnedFromService = Object.assign(
          {
            instrumento: 'BBBBBB',
            descripcion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CatTipoMonetario', () => {
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
