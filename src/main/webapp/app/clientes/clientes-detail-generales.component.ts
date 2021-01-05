import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UtilService } from '../core/service/util.service';
import { DatosGeneralesCliente } from '../shared/model/cliente.model';

@Component({
  selector: 'jhi-clientes-detail-generales',
  templateUrl: './clientes-detail-generales.component.html',
})
export class ClientesDetailGeneralesComponent implements OnInit {
  @Input() datosGeneralesCliente!: DatosGeneralesCliente;

  constructor(protected activatedRoute: ActivatedRoute, protected utilService: UtilService) {
    this.datosGeneralesCliente = new DatosGeneralesCliente();
  }

  ngOnInit(): void {}

  previousState(): void {
    window.history.back();
  }

  byteSize(base64String: string): string {
    return this.utilService.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.utilService.openFile(contentType, base64String);
  }
}
