import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatCP } from '../../shared/model/cat-cp.model';
import { ICatRiesgo } from '../../shared/model/cat-riesgo.model';
import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { ICatEstado } from '../../shared/model/cat-estado.model';

import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { CatCPService } from './cat-cp.service';
import { CatEstadoService } from '../cat-estado/cat-estado.service';
import { CatMunicipioService } from '../cat-municipio/cat-municipio.service';
import { CatRiesgoService } from '../cat-riesgo/cat-riesgo.service';
import { CatCPDeleteDialogComponent } from './cat-cp-delete-dialog.component';
import { FilterService } from '../../shared/util/filter.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-cat-cp',
  templateUrl: './cat-cp.component.html',
})
export class CatCPComponent implements OnInit, OnDestroy {
  catCPS?: ICatCP[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filterId: any;
  filterEstado: any = null;
  filterMunicipio: any = null;
  filterRiesgo: any = null;
  catEstados: ICatEstado[] = [];
  catMunicipios: ICatMunicipio[] = [];
  catRiesgos: ICatRiesgo[] = [];

  constructor(
    protected catCPService: CatCPService,
    protected catMunicipioService: CatMunicipioService,
    protected catEstadoService: CatEstadoService,
    protected catRiesgoService: CatRiesgoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected filterService: FilterService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    const pnames = ['id.contains', 'estadoId.equals', 'municipioId.equals', 'riesgoId.equals'];
    const reqParams = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    const filterParams = [this.filterId, this.filterEstado, this.filterMunicipio, this.filterRiesgo];
    this.filterService.getReqParamsObject(reqParams, filterParams, pnames);

    this.catCPService.query(reqParams).subscribe(
      (res: HttpResponse<ICatCP[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatCPS();

    this.catEstadoService
      .query({ page: 0, size: 50 })
      .pipe(
        map((res: HttpResponse<ICatEstado[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatEstado[]) => (this.catEstados = resBody));

    this.catRiesgoService
      .query({ page: 0, size: 50 })
      .pipe(
        map((res: HttpResponse<ICatRiesgo[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatRiesgo[]) => (this.catRiesgos = resBody));
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

  trackId(index: number, item: ICatCP): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatCPS(): void {
    this.eventSubscriber = this.eventManager.subscribe('catCPListModification', () => this.loadPage());
  }

  delete(catCP: ICatCP): void {
    const modalRef = this.modalService.open(CatCPDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catCP = catCP;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatCP[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/cat-cp'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.catCPS = data || [];
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
    this.filterEstado = null;
    this.filterMunicipio = null;
    this.filterRiesgo = null;
    this.catMunicipios = [];
    this.loadPage(1, true);
  }
}
