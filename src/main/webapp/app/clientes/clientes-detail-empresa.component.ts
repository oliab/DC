import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UtilService } from '../core/service/util.service';
import { DatosEmpresaCliente } from '../shared/model/cliente.model';

@Component({
  selector: 'jhi-clientes-detail-empresa',
  templateUrl: './clientes-detail-empresa.component.html',
})
export class ClientesDetailEmpresaComponent implements OnInit {
  @Input() datosEmpresaCliente!: DatosEmpresaCliente;

  constructor(protected activatedRoute: ActivatedRoute, protected utilService: UtilService) {
    this.datosEmpresaCliente = new DatosEmpresaCliente();
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
