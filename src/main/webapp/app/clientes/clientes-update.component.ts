import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ClienteDTO, DatosDireccionCliente, DatosEmpresaCliente, DatosGeneralesCliente, ICliente } from '../shared/model/cliente.model';
import { ClientesService } from './clientes.service';
import { ClientesDatosGeneralesComponent } from './clientes-datos-generales.component';
import { ClientesDatosDireccionComponent } from './clientes-datos-direccion.component';
import { TipoEmpresaENUM } from '../entities/cat-tipo-empresa/cat-tipo-empresa.enums';
import { ClientesDatosEmpresaComponent } from './clientes-datos-empresa.component';

declare const $: any;

@Component({
  selector: 'jhi-clientes-update',
  templateUrl: './clientes-update.component.html',
})
export class ClientesUpdateComponent implements OnInit {
  isSaving = false;
  tipoCliente: number;

  @ViewChild('cDatosGenerales', { static: true }) cDatosGenerales!: ClientesDatosGeneralesComponent;
  @ViewChild('cDatosDireccion', { static: true }) cDatosDireccion!: ClientesDatosDireccionComponent;
  @ViewChild('cDatosEmpresa', { static: true }) cDatosEmpresa!: ClientesDatosEmpresaComponent;
  @ViewChild('cDatosDireccionEmpresa', { static: true }) cDatosDireccionEmpresa!: ClientesDatosDireccionComponent;

  constructor(protected clienteService: ClientesService, protected activatedRoute: ActivatedRoute) {
    this.tipoCliente = TipoEmpresaENUM.FISICA;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      console.error(cliente);
      // this.updateForm(cliente);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    // eslint-disable-next-line no-debugger
    debugger;

    const cliente = new ClienteDTO();

    const fDatosGeneralesCliente = new DatosGeneralesCliente();
    if (this.cDatosGenerales.isValidDatosGeneralesUsuario()) {
      Object.assign(fDatosGeneralesCliente, this.cDatosGenerales.getDatosGeneresUsuario());
    } else {
      this.activeTab(0);
      return;
    }

    const fDatosDireccionCliente = new DatosDireccionCliente();
    if (this.cDatosDireccion.isValidDatosDireccionUsuario()) {
      Object.assign(fDatosDireccionCliente, this.cDatosDireccion.getDatosDireccionUsuario());
    } else {
      this.activeTab(1);
      return;
    }

    Object.assign(cliente, {
      datosGeneralesCliente: fDatosGeneralesCliente || null,
      datosDireccionCliente: fDatosDireccionCliente || null,
    });

    if (fDatosGeneralesCliente.tipoClienteId !== TipoEmpresaENUM.FISICA) {
      const fDatosEmpresaCliente = new DatosEmpresaCliente();
      if (this.cDatosEmpresa.isValidDatosEmpresaUsuario()) {
        Object.assign(fDatosEmpresaCliente, this.cDatosEmpresa.getDatosEmpresaUsuario());
      } else {
        this.activeTab(2);
        return;
      }

      const fDatosDireccionEmpresa = new DatosDireccionCliente();
      if (this.cDatosDireccionEmpresa.isValidDatosDireccionUsuario()) {
        Object.assign(fDatosDireccionEmpresa, this.cDatosDireccionEmpresa.getDatosDireccionUsuario());
      } else {
        this.activeTab(3);
        return;
      }

      Object.assign(cliente, {
        datosEmpresaCliente: fDatosEmpresaCliente || null,
        datosDireccionEmpresa: fDatosDireccionEmpresa || null,
      });
    }

    this.isSaving = true;
    alert('guardar');
    /* const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    } */

    this.subscribeToSaveResponse(this.clienteService.nuevo(cliente));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  onTipoEmpresa(tipoClienteId: number): void {
    this.tipoCliente = tipoClienteId;
    if (this.tipoCliente === TipoEmpresaENUM.FISICA) {
      $('.tipoEmpresa').addClass('d-none');
    } else {
      $('.tipoEmpresa').removeClass('d-none');
    }
  }

  activeTab(index: number): void {
    $('.nav-tabs li:eq(' + index + ') a').tab('show');
  }
}
