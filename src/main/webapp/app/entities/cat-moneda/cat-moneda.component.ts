import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatMoneda } from '../../shared/model/cat-moneda.model';
import { ICatPais } from '../../shared/model/cat-pais.model';

import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { CatMonedaService } from './cat-moneda.service';
import { CatPaisService } from '../cat-pais/cat-pais.service';
import { CatMonedaDeleteDialogComponent } from './cat-moneda-delete-dialog.component';
import { FilterService } from '../../shared/util/filter.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'jhi-cat-moneda',
  templateUrl: './cat-moneda.component.html',
})
export class CatMonedaComponent implements OnInit, OnDestroy {
  catMonedas?: ICatMoneda[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filterId: any;
  filterNombre: any = null;
  filterPais: any = null;
  catPaises: ICatPais[] = [];

  constructor(
    protected catMonedaService: CatMonedaService,
    protected catPaisService: CatPaisService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected filterService: FilterService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    const pnames = ['id.contains', 'moneda.contains', 'paisId.equals'];
    const reqParams = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    const filterParams = [this.filterId, this.filterNombre, this.filterPais];
    this.filterService.getReqParamsObject(reqParams, filterParams, pnames);

    this.catMonedaService.query(reqParams).subscribe(
      (res: HttpResponse<ICatMoneda[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatMonedas();

    this.catPaisService
      .query({ page: 0, size: 250 })
      .pipe(
        map((res: HttpResponse<ICatPais[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatPais[]) => (this.catPaises = resBody));
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

  trackId(index: number, item: ICatMoneda): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatMonedas(): void {
    this.eventSubscriber = this.eventManager.subscribe('catMonedaListModification', () => this.loadPage());
  }

  delete(catMoneda: ICatMoneda): void {
    const modalRef = this.modalService.open(CatMonedaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catMoneda = catMoneda;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatMoneda[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/cat-moneda'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.catMonedas = data || [];
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
    this.filterPais = null;
    this.loadPage(1, true);
  }
}
