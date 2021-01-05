import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { CatNacionalidadService } from '../../entities/cat-nacionalidad/cat-nacionalidad.service';
import { CatPaisService } from '../../entities/cat-pais/cat-pais.service';
import { CatEstadoService } from '../../entities/cat-estado/cat-estado.service';
import { CatMunicipioService } from '../../entities/cat-municipio/cat-municipio.service';
import { CatLocalidadService } from '../../entities/cat-localidad/cat-localidad.service';
import { CatCPService } from '../../entities/cat-cp/cat-cp.service';
import { ICatNacionalidad } from '../../shared/model/cat-nacionalidad.model';
import { ICatPais } from '../../shared/model/cat-pais.model';
import { ICatEstado } from '../../shared/model/cat-estado.model';
import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { ICatLocalidad } from '../../shared/model/cat-localidad.model';
import { ICatCP } from '../../shared/model/cat-cp.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DireccionesService {
  constructor(
    protected http: HttpClient,
    protected catNacionalidadService: CatNacionalidadService,
    protected catPaisService: CatPaisService,
    protected catEstadoService: CatEstadoService,
    protected catMunicipioService: CatMunicipioService,
    protected catLocalidadService: CatLocalidadService,
    protected catCPService: CatCPService
  ) {}

  getNacionalidadById(id: number): Observable<ICatNacionalidad> {
    return new Observable(observer => {
      this.catNacionalidadService.find(id).subscribe((res: HttpResponse<ICatNacionalidad>) => {
        observer.next(res.body as ICatNacionalidad);
        observer.complete();
      });
    });
  }

  getNacionalidades(ppage: number, psize: number): Observable<Array<ICatNacionalidad>> {
    return new Observable(observer => {
      this.catNacionalidadService
        .query({
          page: ppage,
          size: psize,
        })
        .subscribe((res: HttpResponse<ICatNacionalidad[]>) => {
          observer.next(res.body || []);
          observer.complete();
        });
    });
  }

  getPaises(ppage: number, psize: number): Observable<Array<ICatPais>> {
    return new Observable(observer => {
      this.catPaisService
        .query({
          page: ppage,
          size: psize,
        })
        .subscribe((res: HttpResponse<ICatPais[]>) => {
          observer.next(res.body || []);
          observer.complete();
        });
    });
  }

  getEstados(ppage: number, psize: number): Observable<Array<ICatEstado>> {
    return new Observable(observer => {
      this.catEstadoService
        .query({
          page: ppage,
          size: psize,
        })
        .subscribe((res: HttpResponse<ICatEstado[]>) => {
          observer.next(res.body || []);
          observer.complete();
        });
    });
  }

  getMunicipios(estadoId: string, ppage: number, psize: number): Observable<Array<ICatMunicipio>> {
    const param: any = { 'estadoId.equals': estadoId, page: ppage, size: psize };
    return new Observable(observer => {
      this.catMunicipioService.query(param).subscribe((res: HttpResponse<ICatMunicipio[]>) => {
        observer.next(res.body || []);
        observer.complete();
      });
    });
  }

  getLocalidades(estadoId: string, municipioId: string, ppage: number, psize: number): Observable<Array<ICatLocalidad>> {
    const param: any = {
      'estadoId.equals': estadoId,
      'municipioId.equals': municipioId,
      page: ppage,
      size: psize,
    };

    return new Observable(observer => {
      this.catLocalidadService.query(param).subscribe((res: HttpResponse<ICatLocalidad[]>) => {
        observer.next(res.body || []);
        observer.complete();
      });
    });
  }

  getCPs(estadoId: string, municipioId: string, ppage: number, psize: number): Observable<Array<ICatCP>> {
    const param: any = {
      'estadoId.equals': estadoId,
      'municipioId.equals': municipioId,
      page: ppage,
      size: psize,
    };

    return new Observable(observer => {
      this.catCPService.query(param).subscribe((res: HttpResponse<ICatCP[]>) => {
        observer.next(res.body || []);
        observer.complete();
      });
    });
  }
}
