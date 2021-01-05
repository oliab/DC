import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ICliente, DatosGeneralesCliente } from '../shared/model/cliente.model';
import { ClientesService } from './clientes.service';
import { ICatTipoEmpresa } from '../shared/model/cat-tipo-empresa.model';
import { CatTipoEmpresaService } from '../entities/cat-tipo-empresa/cat-tipo-empresa.service';
import { ICatIdentificacion } from '../shared/model/cat-identificacion.model';
import { CatIdentificacionService } from '../entities/cat-identificacion/cat-identificacion.service';
import { ICatSector } from '../shared/model/cat-sector.model';
import { CatSectorService } from '../entities/cat-sector/cat-sector.service';
import { ICatMoneda } from '../shared/model/cat-moneda.model';
import { CatMonedaService } from '../entities/cat-moneda/cat-moneda.service';
import { ExpedienteCliente } from 'app/shared/model/expediente-cliente.model';
import { UtilService } from 'app/core/service/util.service';

@Component({
  selector: 'jhi-clientes-datos-generales',
  templateUrl: './clientes-datos-generales.component.html',
})
export class ClientesDatosGeneralesComponent implements OnInit {
  datosGeneralesCliente: DatosGeneralesCliente;
  cattipoempresas: ICatTipoEmpresa[];
  catidentificacions: ICatIdentificacion[];
  catsectors: ICatSector[];
  catmonedas: ICatMoneda[];
  fechaAltaDp: any;
  fechaActDp: any;
  fechaNacimientoDp: any;

  editForm!: FormGroup;

  @Output() onTipoEmpresa = new EventEmitter<number>();

  constructor(
    protected clienteService: ClientesService,
    protected catTipoEmpresaService: CatTipoEmpresaService,
    protected catIdentificacionService: CatIdentificacionService,
    protected catSectorService: CatSectorService,
    protected catMonedaService: CatMonedaService,
    protected utilService: UtilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.datosGeneralesCliente = new DatosGeneralesCliente();
    this.datosGeneralesCliente.comprobanteIdentificacion = new ExpedienteCliente();
    this.datosGeneralesCliente.comprobanteIngresos = new ExpedienteCliente();
    this.cattipoempresas = [];
    this.catidentificacions = [];
    this.catsectors = [];
    this.catmonedas = [];
    this.initFormulario();
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });

    this.initCatalogos();
    $('#file_comprobanteIngresos').on('change', () => {
      const fileName = ($('#file_comprobanteIngresos').val() as string).split('\\').pop() as string;
      $('#file_comprobanteIngresos').siblings('.custom-file-label').addClass('selected').html(fileName);
    });

    $('#file_comprobanteIdentificacion').on('change', () => {
      const fileName = ($('#file_comprobanteIdentificacion').val() as string).split('\\').pop() as string;
      $('#file_comprobanteIdentificacion').siblings('.custom-file-label').addClass('selected').html(fileName);
    });
  }

  private initFormulario(): void {
    this.editForm = this.fb.group({
      id: [],
      login: [
        null,
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(50),
          Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
        ],
      ],
      firstName: [null, [Validators.required, Validators.maxLength(50)]],
      lastName: [null, [Validators.required, Validators.maxLength(50)]],
      mLastName: [null, [Validators.required, Validators.maxLength(50)]],
      email: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
      fechaNacimiento: [null, [Validators.required]],
      noIdentificacion: [null, [Validators.required, Validators.maxLength(100)]],
      ingresos: [null, [Validators.required]],
      estimacionOperacion: [null, [Validators.required]],
      telefono: [null, [Validators.maxLength(50)]],
      fechaAlta: [null, []],
      fechaAct: [null, []],
      tipoCliente: [null, Validators.required],
      tipoIdentificacion: [null, Validators.required],
      sector: [null, Validators.required],
      moneda: [null, Validators.required],
      usuarioAlta: [null, []],
      usuarioAct: [null, []],
      comprobanteIdentificacion: [null, [Validators.required]],
      comprobanteIdentificacionContentType: [null, [Validators.required]],
      descripcionIdentificacion: [null, []],
      comprobanteIngresos: [null, [Validators.required]],
      descripcionIngresos: [null, []],
      comprobanteIngresosContentType: [null, [Validators.required]],
    });
  }

  private initCatalogos(): void {
    this.catTipoEmpresaService.query().subscribe((res: HttpResponse<ICatTipoEmpresa[]>) => (this.cattipoempresas = res.body || []));
    this.catIdentificacionService
      .query()
      .subscribe((res: HttpResponse<ICatIdentificacion[]>) => (this.catidentificacions = res.body || []));
    this.catSectorService.query({ page: 0, size: 500 }).subscribe((res: HttpResponse<ICatSector[]>) => (this.catsectors = res.body || []));
    this.catMonedaService.query({ page: 0, size: 250 }).subscribe((res: HttpResponse<ICatMoneda[]>) => (this.catmonedas = res.body || []));
  }

  private updateDatosGenerales(): void {
    Object.assign(this.datosGeneralesCliente, {
      tipoCliente: this.cattipoempresas.find(ele => ele.id === this.datosGeneralesCliente.tipoClienteId),
      tipoIdentificacion: this.catidentificacions.find(ele => ele.id === this.datosGeneralesCliente.tipoIdentificacionId),
      sector: this.catsectors.find(ele => ele.id === this.datosGeneralesCliente.actividadEconomicaId),
      moneda: this.catmonedas.find(ele => ele.id === this.datosGeneralesCliente.monedaId),
      identificacionContentType: this.editForm.get(['comprobanteIdentificacionContentType'])!.value,
      comprobanteIdentificacion: this.editForm.get(['comprobanteIdentificacion'])!.value,
      ingresosContentType: this.editForm.get(['comprobanteIngresosContentType'])!.value,
      comprobanteIngresos: this.editForm.get(['comprobanteIngresos'])!.value,
    });
  }

  private updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      noIdentificacion: cliente.noIdentificacion,
      ingresos: cliente.ingresos,
      estimacionOperacion: cliente.estimacionOperacion,
      telefono: cliente.telefono,
      fechaAlta: cliente.fechaAlta,
      fechaAct: cliente.fechaAct,
      usuario: cliente.usuario,
      tipoCliente: cliente.tipoCliente,
      tipoIdentificacion: cliente.tipoIdentificacion,
      sector: cliente.sector,
      moneda: cliente.moneda,
      usuarioAlta: cliente.usuarioAlta,
      usuarioAct: cliente.usuarioAct,
    });
  }

  byteSize(base64String: string): string {
    return this.utilService.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.utilService.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.utilService.setFileData(event, field, isImage, this.editForm);
  }

  onTipoCliente(): void {
    this.onTipoEmpresa.emit(this.datosGeneralesCliente.tipoClienteId);
  }

  isValidDatosGeneralesUsuario(): boolean {
    this.editForm.markAllAsTouched();
    return this.editForm.valid;
  }

  getDatosGeneresUsuario(): any {
    if (this.isValidDatosGeneralesUsuario()) {
      this.updateDatosGenerales();
      return this.datosGeneralesCliente;
    }

    return null;
  }
}
