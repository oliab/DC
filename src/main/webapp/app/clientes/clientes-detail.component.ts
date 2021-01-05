import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UtilService } from 'app/core/service/util.service';
import { TipoEmpresaENUM } from 'app/entities/cat-tipo-empresa/cat-tipo-empresa.enums';
import { ClienteDTO } from '../shared/model/cliente.model';

@Component({
  selector: 'jhi-clientes-detail',
  templateUrl: './clientes-detail.component.html',
})
export class ClientesDetailComponent implements OnInit {
  cliente: ClienteDTO;
  TipoEmpresaEnum: any;

  constructor(protected activatedRoute: ActivatedRoute, protected utilService: UtilService) {
    this.cliente = new ClienteDTO();
    this.TipoEmpresaEnum = TipoEmpresaENUM;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => (this.cliente = cliente));
  }

  previousState(): void {
    window.history.back();
  }
}
