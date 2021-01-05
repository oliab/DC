import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExpedienteCliente } from 'app/shared/model/expediente-cliente.model';

@Component({
  selector: 'jhi-expediente-cliente-detail',
  templateUrl: './expediente-cliente-detail.component.html',
})
export class ExpedienteClienteDetailComponent implements OnInit {
  expedienteCliente: IExpedienteCliente | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expedienteCliente }) => (this.expedienteCliente = expedienteCliente));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
