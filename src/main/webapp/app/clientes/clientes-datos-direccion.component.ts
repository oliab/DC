import { AfterViewInit, Component, Input, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { DireccionesService } from '../../app/core/service/direcciones.service';
import { ClientesService } from './clientes.service';
import { UtilService } from '../../app/core/service/util.service';
import { ICatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';
import { ICatPais } from 'app/shared/model/cat-pais.model';
import { ICatEstado } from 'app/shared/model/cat-estado.model';
import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';
import { ICatLocalidad } from 'app/shared/model/cat-localidad.model';
import { ICatCP } from 'app/shared/model/cat-cp.model';
import { NacionalidadesENUM } from 'app/entities/cat-nacionalidad/cat-nacionalidad.enums';
import { PaisesENUM } from 'app/entities/cat-pais/cat-pais.enums';
import { isNullOrUndefined } from 'util';
import { ORDEN_DIV_GEOPOLITICA } from 'app/core/enums/domicilios.enums';
import { DatosDireccionCliente } from 'app/shared/model/cliente.model';

@Component({
  selector: 'jhi-clientes-datos-direccion',
  templateUrl: './clientes-datos-direccion.component.html',
})
export class ClientesDatosDireccionComponent implements OnInit, AfterViewInit {
  datosDireccionCliente: DatosDireccionCliente;
  catnacionalidads: ICatNacionalidad[];
  catpais: ICatPais[];
  catpaisExt: ICatPais[];
  catestados: ICatEstado[];
  catmunicipios: ICatMunicipio[];
  catlocalidads: ICatLocalidad[];
  catcps: ICatCP[];
  NacionalidadEnum: any;
  PaisEnum: any;

  @Input() tipoCliente: string;

  editForm = this.fb.group({
    id: [],
    nacionalidad: [null, [Validators.required]],
    paisOrigen: [null, [Validators.required]],
    pais: [null, [Validators.required]],
    estado: [{ value: null, disabled: true }, [Validators.required]],
    municipio: [{ value: null, disabled: true }, [Validators.required]],
    localidad: [{ value: null, disabled: true }],
    cp: [{ value: null, disabled: true }, [Validators.required]],
    calle: [null, [Validators.required]],
    colonia: [null, [Validators.required]],
    noExt: [null, [Validators.required]],
    noInt: [null, []],
    domicilio: [null, [Validators.required]],
    fechaAct: [null, []],
    comprobanteDomicilio: [null, [Validators.required]],
    comprobanteDomicilioContentType: [null, [Validators.required]],
    descripcionDomicilio: [null, []],
  });

  constructor(
    protected clienteService: ClientesService,
    protected direccionesService: DireccionesService,
    protected utilService: UtilService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.NacionalidadEnum = NacionalidadesENUM;
    this.PaisEnum = PaisesENUM;
    this.datosDireccionCliente = new DatosDireccionCliente();
    this.catnacionalidads = [];
    this.catpais = [];
    this.catpaisExt = [];
    this.catestados = [];
    this.catmunicipios = [];
    this.catlocalidads = [];
    this.catcps = [];
    this.tipoCliente = 'domicilioUsuario';
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      console.error(cliente);
      this.updateForm();
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
  }

  private async initCatalogos(): Promise<void> {
    this.catnacionalidads = await this.direccionesService.getNacionalidades(0, 10).toPromise();
    this.catpais = await this.direccionesService.getPaises(0, 250).toPromise();
    this.catpaisExt = this.catpais;
    this.catestados = await this.direccionesService.getEstados(0, 50).toPromise();
  }

  private enableControlsPais(): void {
    if (this.datosDireccionCliente.domicilioUsuario.paisId === PaisesENUM.MEXICO) {
      this.utilService.enableControls(
        this.editForm,
        ['estado', 'municipio', 'localidad', 'cp', 'colonia', 'calle', 'noExt', 'noInt'],
        true
      );
      this.utilService.enableControls(this.editForm, ['domicilio'], false, true);
    } else {
      this.utilService.enableControls(
        this.editForm,
        ['estado', 'municipio', 'localidad', 'cp', 'colonia', 'calle', 'noExt', 'noInt'],
        false,
        true
      );
      this.utilService.enableControls(this.editForm, ['domicilio'], true);
    }
  }

  private resetUbicacion(divGeoPolitica?: number): void {
    if (!isNullOrUndefined(divGeoPolitica)) {
      this.datosDireccionCliente.domicilioUsuario.localidadId = undefined;
      this.catlocalidads = [];
      this.datosDireccionCliente.domicilioUsuario.cpId = undefined;
      this.catcps = [];

      if (divGeoPolitica >= ORDEN_DIV_GEOPOLITICA.ESTADO) {
        this.datosDireccionCliente.domicilioUsuario.municipioId = undefined;
        this.catmunicipios = [];
      }

      if (divGeoPolitica >= ORDEN_DIV_GEOPOLITICA.PAIS) {
        this.datosDireccionCliente.domicilioUsuario.estadoId = undefined;
        this.datosDireccionCliente.domicilioUsuario.municipioId = undefined;
        this.catmunicipios = [];
      }
    }
  }

  private updateDomicilioUsuario(): void {
    Object.assign(this.datosDireccionCliente.domicilioUsuario, {
      nacionalidad: this.catnacionalidads.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.nacionalidadId),
      paisOrigen: this.catpais.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.paisOrigenId),
      pais: this.catpais.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.paisId),
      estado: this.catestados.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.estadoId),
      municipio: this.catmunicipios.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.municipioId),
      localidad: this.catlocalidads.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.localidadId),
      cp: this.catcps.find(ele => ele.id === this.datosDireccionCliente.domicilioUsuario.cpId),
    });

    Object.assign(this.datosDireccionCliente, {
      domicilioContentType: this.editForm.get(['comprobanteDomicilioContentType'])!.value,
      comprobanteDomicilio: this.editForm.get(['comprobanteDomicilio'])!.value,
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

  updateForm(): void {
    this.editForm.patchValue({
      nacionalidad: [null, [Validators.required]],
      paisOrigen: [null, [Validators.required]],
      pais: [null, [Validators.required]],
      estado: [{ value: null, disabled: true }, [Validators.required]],
      municipio: [{ value: null, disabled: true }, [Validators.required]],
      localidad: [{ value: null, disabled: true }],
      cp: [{ value: null, disabled: true }, [Validators.required]],
    });
  }

  onNacionalidad(): void {
    this.datosDireccionCliente.domicilioUsuario.paisOrigenId = undefined;
    if (this.datosDireccionCliente.domicilioUsuario.nacionalidadId === NacionalidadesENUM.MEXICANO) {
      this.datosDireccionCliente.domicilioUsuario.paisOrigenId = PaisesENUM.MEXICO;
      this.catpaisExt = this.catpais;
    } else {
      this.catpaisExt = this.catpais.filter(ele => ele.id !== PaisesENUM.MEXICO);
    }
  }

  onPais(): void {
    this.resetUbicacion(ORDEN_DIV_GEOPOLITICA.PAIS);
    this.enableControlsPais();
  }

  async onEstado(): Promise<void> {
    this.resetUbicacion(ORDEN_DIV_GEOPOLITICA.ESTADO);
    this.catmunicipios = await this.direccionesService
      .getMunicipios(this.datosDireccionCliente.domicilioUsuario.estadoId!, 0, 250)
      .toPromise();
  }

  async onMunicipio(): Promise<void> {
    this.resetUbicacion(ORDEN_DIV_GEOPOLITICA.MUNICIPIO);

    this.catlocalidads = await this.direccionesService
      .getLocalidades(
        this.datosDireccionCliente.domicilioUsuario.estadoId!,
        this.datosDireccionCliente.domicilioUsuario.municipioId!,
        0,
        250
      )
      .toPromise();

    this.catcps = await this.direccionesService
      .getCPs(this.datosDireccionCliente.domicilioUsuario.estadoId!, this.datosDireccionCliente.domicilioUsuario.municipioId!, 0, 250)
      .toPromise();
  }

  isValidDatosDireccionUsuario(): boolean {
    this.editForm.markAllAsTouched();
    return this.editForm.valid;
  }

  getDatosDireccionUsuario(): any {
    if (this.isValidDatosDireccionUsuario()) {
      this.updateDomicilioUsuario();
      return this.datosDireccionCliente;
    }

    return null;
  }
}
