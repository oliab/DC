import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ICatPais } from '../../shared/model/cat-pais.model';
import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { CatPaisService } from './cat-pais.service';
import { CatPaisDeleteDialogComponent } from './cat-pais-delete-dialog.component';
import { FilterService } from '../../shared/util/filter.service';

@Component({
  selector: 'jhi-cat-pais',
  templateUrl: './cat-pais.component.html',
  providers: [FilterService],
})
export class CatPaisComponent implements OnInit, OnDestroy {
  catPais?: ICatPais[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filterId: any;
  filterNombre: any;
  filterCodigo: any;
  filterParams: Array<any> = [];

  constructor(
    protected catPaisService: CatPaisService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected filterService: FilterService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.filterParams = [this.filterId, this.filterNombre, this.filterCodigo];
    const pnames = ['id.contains', 'nombre.contains', 'codigoA3.contains'];

    const reqParams = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    this.filterService.getReqParamsObject(reqParams, this.filterParams, pnames);

    this.catPaisService.query(reqParams).subscribe(
      (res: HttpResponse<ICatPais[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatPais();
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

  trackId(index: number, item: ICatPais): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatPais(): void {
    this.eventSubscriber = this.eventManager.subscribe('catPaisListModification', () => this.loadPage());
  }

  delete(catPais: ICatPais): void {
    const modalRef = this.modalService.open(CatPaisDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catPais = catPais;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatPais[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/cat-pais'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.catPais = data || [];
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
    this.filterCodigo = null;
    this.loadPage(1, true);
  }
}
