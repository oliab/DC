import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatSector } from '../../shared/model/cat-sector.model';

import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { CatSectorService } from './cat-sector.service';
import { CatSectorDeleteDialogComponent } from './cat-sector-delete-dialog.component';
import { FilterService } from '../../shared/util/filter.service';

@Component({
  selector: 'jhi-cat-sector',
  templateUrl: './cat-sector.component.html',
})
export class CatSectorComponent implements OnInit, OnDestroy {
  catSectors?: ICatSector[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  filterId: any = null;
  filterActividad: any = null;
  filterVulnereable: any = null;

  constructor(
    protected catSectorService: CatSectorService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected filterService: FilterService
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    const pnames = ['id.contains', 'actividadEconomica.contains', 'actividadVulnerable.equals'];
    const reqParams = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };

    const filterParams = [this.filterId, this.filterActividad, this.filterVulnereable];
    this.filterService.getReqParamsObject(reqParams, filterParams, pnames);

    this.catSectorService.query(reqParams).subscribe(
      (res: HttpResponse<ICatSector[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInCatSectors();
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

  trackId(index: number, item: ICatSector): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatSectors(): void {
    this.eventSubscriber = this.eventManager.subscribe('catSectorListModification', () => this.loadPage());
  }

  delete(catSector: ICatSector): void {
    const modalRef = this.modalService.open(CatSectorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catSector = catSector;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ICatSector[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/cat-sector'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.catSectors = data || [];
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
    this.filterActividad = null;
    this.filterVulnereable = null;
    this.loadPage(1, true);
  }
}
