import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PaisesENUM } from 'app/entities/cat-pais/cat-pais.enums';
import { UtilService } from '../core/service/util.service';
import { DatosDireccionCliente } from '../shared/model/cliente.model';

@Component({
  selector: 'jhi-clientes-detail-direccion',
  templateUrl: './clientes-detail-direccion.component.html',
})
export class ClientesDetailDireccionComponent implements OnInit {
  @Input() datosDireccionCliente!: DatosDireccionCliente;
  PaisEnum: any;

  constructor(protected activatedRoute: ActivatedRoute, protected utilService: UtilService) {
    this.datosDireccionCliente = new DatosDireccionCliente();
    this.PaisEnum = PaisesENUM;
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
