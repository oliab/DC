import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { ICatEstado } from '../../shared/model/cat-estado.model';

import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { CatEstadoService } from '../cat-estado/cat-estado.service';
import { CatMunicipioService } from './cat-municipio.service';
import { CatMunicipioDeleteDialogComponent } from './cat-municipio-delete-dialog.component';
import { FilterService } from '../../shared/util/filter.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-cat-municipio',
  templateUrl: './cat-municipio.component.html',
})
export class CatMunicipioComponent implements OnInit, OnDestroy {
  catMunicipios?: ICatMunicipio[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filterId: any;
  filterNombre: any;
  filterEstado: any = null;
  catEstados: ICatEstado[] = [];

  constructor(
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

    const filterParams = [this.filterId, this.filterNombre, this.filterEstado];
    const pnames = ['id.contains', 'nombre.contains', 'estadoId.equals'];

    const reqParams = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.filterService.getReqParamsObject(reqParams, filterParams, pnames);

    this.catMunicipioService.query(reqParams).subscribe(
      (res: HttpResponse<ICatMunicipio[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatMunicipios();

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

  trackId(index: number, item: ICatMunicipio): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatMunicipios(): void {
    this.eventSubscriber = this.eventManager.subscribe('catMunicipioListModification', () => this.loadPage());
  }

  delete(catMunicipio: ICatMunicipio): void {
    const modalRef = this.modalService.open(CatMunicipioDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catMunicipio = catMunicipio;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatMunicipio[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/cat-municipio'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.catMunicipios = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  onBuscar(): void {
    this.loadPage(1, true);
  }

  onLimpiar(): void {
    this.filterId = null;
    this.filterNombre = null;
    this.filterEstado = null;
    this.loadPage(1, true);
  }
}
