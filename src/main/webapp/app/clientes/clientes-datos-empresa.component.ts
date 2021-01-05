import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { ICliente, DatosEmpresaCliente } from '../shared/model/cliente.model';
import { ClientesService } from './clientes.service';
import { ICatIdentificacion } from '../shared/model/cat-identificacion.model';
import { CatIdentificacionService } from '../entities/cat-identificacion/cat-identificacion.service';
import { UtilService } from 'app/core/service/util.service';

@Component({
  selector: 'jhi-clientes-datos-empresa',
  templateUrl: './clientes-datos-empresa.component.html',
})
export class ClientesDatosEmpresaComponent implements OnInit, AfterViewInit {
  datosEmpresaCliente: DatosEmpresaCliente;
  catidentificacions: ICatIdentificacion[];
  fechaAltaDp: any;
  fechaActDp: any;
  fechaNacimientoDp: any;

  editForm!: FormGroup;

  @Output() onTipoEmpresa = new EventEmitter<number>();
  @Input() tipoCliente: string;

  constructor(
    protected clienteService: ClientesService,
    protected utilService: UtilService,
    protected catIdentificacionService: CatIdentificacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.datosEmpresaCliente = new DatosEmpresaCliente();
    this.catidentificacions = [];
    this.tipoCliente = 'datosEmpresa';
    this.initFormulario();
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });

    this.initCatalogos();
  }

  ngAfterViewInit(): void {
    $('.' + this.tipoCliente).on('change', () => {
      const fileName = ($('.' + this.tipoCliente).val() as string).split('\\').pop() as string;
      $('.' + this.tipoCliente)
        .siblings('.custom-file-label')
        .addClass('selected')
        .html(fileName);
    });

    $('#file_comprobanteFirma').on('change', () => {
      const fileName = ($('#file_comprobanteFirma').val() as string).split('\\').pop() as string;
      $('#file_comprobanteFirma').siblings('.custom-file-label').addClass('selected').html(fileName);
    });
  }

  private initFormulario(): void {
    this.editForm = this.fb.group({
      id: [],
      rfc: [
        null,
        [
          Validators.required,
          Validators.minLength(12),
          Validators.pattern(
            /^(([ÑA-Z|ña-z|&]{3}|[A-Z|a-z]{4})\d{2}((0[1-9]|1[012])(0[1-9]|1\d|2[0-8])|(0[13456789]|1[012])(29|30)|(0[13578]|1[02])31)(\w{2})([A|a|0-9]{1}))$|^(([ÑA-Z|ña-z|&]{3}|[A-Z|a-z]{4})([02468][048]|[13579][26])0229)(\w{2})([A|a|0-9]{1})$/
          ),
        ],
      ],
      razonSocial: [null, [Validators.required, Validators.maxLength(250)]],
      noIdentificacion: [null, [Validators.required, Validators.maxLength(100)]],
      telefono: [null, [Validators.maxLength(50)]],
      fechaAlta: [null, []],
      fechaAct: [null, []],
      tipoIdentificacion: [null, Validators.required],
      usuarioAlta: [null, []],
      usuarioAct: [null, []],
      comprobanteIdentificacion: [null, [Validators.required]],
      comprobanteIdentificacionContentType: [null, [Validators.required]],
      descripcionIdentificacion: [null, []],
      comprobanteFirma: [null, [Validators.required]],
      comprobanteFirmaContentType: [null, [Validators.required]],
      descripcionFirma: [null, []],
    });
  }

  private initCatalogos(): void {
    this.catIdentificacionService
      .query()
      .subscribe((res: HttpResponse<ICatIdentificacion[]>) => (this.catidentificacions = res.body || []));
  }

  private updateDatosEmpresa(): void {
    Object.assign(this.datosEmpresaCliente, {
      tipoIdentificacion: this.catidentificacions.find(ele => ele.id === this.datosEmpresaCliente.tipoIdentificacionId),
      identificacionContentType: this.editForm.get(['comprobanteIdentificacionContentType'])!.value,
      comprobanteIdentificacion: this.editForm.get(['comprobanteIdentificacion'])!.value,
      firmaContentType: this.editForm.get(['comprobanteFirmaContentType'])!.value,
      comprobanteFirma: this.editForm.get(['comprobanteFirma'])!.value,
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

  isValidDatosEmpresaUsuario(): boolean {
    this.editForm.markAllAsTouched();
    return this.editForm.valid;
  }

  getDatosEmpresaUsuario(): any {
    if (this.isValidDatosEmpresaUsuario()) {
      this.updateDatosEmpresa();
      return this.datosEmpresaCliente;
    }

    return null;
  }
}
