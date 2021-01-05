import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatLocalidad } from '../../shared/model/cat-localidad.model';
import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { ICatEstado } from '../../shared/model/cat-estado.model';

import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { CatLocalidadService } from './cat-localidad.service';
import { CatEstadoService } from '../cat-estado/cat-estado.service';
import { CatMunicipioService } from '../cat-municipio/cat-municipio.service';
import { CatLocalidadDeleteDialogComponent } from './cat-localidad-delete-dialog.component';
import { FilterService } from '../../shared/util/filter.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-cat-localidad',
  templateUrl: './cat-localidad.component.html',
})
export class CatLocalidadComponent implements OnInit, OnDestroy {
  catLocalidads?: ICatLocalidad[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filterId: any;
  filterNombre: any = null;
  filterEstado: any = null;
  filterMunicipio: any = null;
  catEstados: ICatEstado[] = [];
  catMunicipios: ICatMunicipio[] = [];

  constructor(
    protected catLocalidadService: CatLocalidadService,
    protected catMunicipioService: CatMunicipioService,
    protected catEstadoService: CatEstadoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected filterService: FilterService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    const pnames = ['id.contains', 'nombre.contains', 'estadoId.equals', 'municipioId.equals'];
    const reqParams = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    const filterParams = [this.filterId, this.filterNombre, this.filterEstado, this.filterMunicipio];
    this.filterService.getReqParamsObject(reqParams, filterParams, pnames);

    this.catLocalidadService.query(reqParams).subscribe(
      (res: HttpResponse<ICatLocalidad[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatLocalidads();

    this.catEstadoService
      .query({ page: 0, size: 50 })
      .pipe(
        map((res: HttpResponse<ICatEstado[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatEstado[]) => (this.catEstados = resBody));
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICatLocalidad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatLocalidads(): void {
    this.eventSubscriber = this.eventManager.subscribe('catLocalidadListModification', () => this.loadPage());
  }

  delete(catLocalidad: ICatLocalidad): void {
    const modalRef = this.modalService.open(CatLocalidadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catLocalidad = catLocalidad;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatLocalidad[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/cat-localidad'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.catLocalidads = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  onEstado(): void {
    this.catMunicipios = [];
    this.filterMunicipio = null;
    const param: any = { 'estadoId.equals': this.filterEstado, page: 0, size: 200 };
    this.catMunicipioService
      .query(param)
      .pipe(
        map((res: HttpResponse<ICatMunicipio[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatMunicipio[]) => (this.catMunicipios = resBody));
  }

  onBuscar(): void {
    this.loadPage(1, true);
  }

  onLimpiar(): void {
    this.filterId = null;
    this.filterNombre = null;
    this.filterEstado = null;
    this.filterMunicipio = null;
    this.catMunicipios = [];
    this.loadPage(1, true);
  }
}
