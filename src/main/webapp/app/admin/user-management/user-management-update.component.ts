import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LanguagesENUM } from '../../core/language/language.constants';
import { NacionalidadesENUM } from '../../entities/cat-nacionalidad/cat-nacionalidad.enums';
import { PaisesENUM } from '../../entities/cat-pais/cat-pais.enums';
import { ORDEN_DIV_GEOPOLITICA } from '../../core/enums/domicilios.enums';
import { User } from '../../core/user/user.model';
import { DatosUsuarioDTO, DatosUsuario } from '../../shared/model/datos-usuario.model';
import { UserService } from '../../core/user/user.service';

import { ICatNacionalidad } from '../../shared/model/cat-nacionalidad.model';
import { CatNacionalidadService } from '../../entities/cat-nacionalidad/cat-nacionalidad.service';
import { ICatPais } from '../../shared/model/cat-pais.model';
import { CatPaisService } from '../../entities/cat-pais/cat-pais.service';
import { ICatEstado } from '../../shared/model/cat-estado.model';
import { CatEstadoService } from '../../entities/cat-estado/cat-estado.service';
import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { CatMunicipioService } from '../../entities/cat-municipio/cat-municipio.service';
import { ICatLocalidad } from '../../shared/model/cat-localidad.model';
import { CatLocalidadService } from '../../entities/cat-localidad/cat-localidad.service';
import { ICatCP } from '../../shared/model/cat-cp.model';
import { CatCPService } from '../../entities/cat-cp/cat-cp.service';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';
import * as moment from 'moment';
import { DomicilioUsuarioDTO, DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';
import { ICatSucursal } from 'app/shared/model/cat-sucursal.model';
import { CatSucursalService } from 'app/entities/cat-sucursal/cat-sucursal.service';
import { UtilService } from 'app/core/service/util.service';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
})
export class UserManagementUpdateComponent implements OnInit {
  user!: User;
  datosUsuario!: DatosUsuarioDTO;
  domicilioUsuario!: DomicilioUsuarioDTO;
  authorities: string[] = [];
  isSaving = false;

  catnacionalidads: ICatNacionalidad[] = [];
  catpais: ICatPais[] = [];
  catpaisExt: ICatPais[] = [];
  catestados: ICatEstado[] = [];
  catmunicipios: ICatMunicipio[] = [];
  catlocalidads: ICatLocalidad[] = [];
  catcps: ICatCP[] = [];
  catsucursales: ICatSucursal[] = [];
  fechaNacimientoDp: any;
  fechaActDp: any;
  NacionalidadEnum: any;
  PaisEnum: any;

  editForm = this.fb.group({
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
    activated: [],
    langKey: [],
    authorities: [null, [Validators.required]],
    fechaNacimiento: [null, [Validators.required]],
    colonia: [{ value: null, disabled: true }, [Validators.required, Validators.maxLength(100)]],
    calle: [{ value: null, disabled: true }, [Validators.required, Validators.maxLength(100)]],
    noExt: [{ value: null, disabled: true }, [Validators.required, Validators.maxLength(20)]],
    noInt: [{ value: null, disabled: true }, [Validators.maxLength(20)]],
    domicilio: [{ value: null, disabled: true }, [Validators.required, Validators.maxLength(512)]],
    fechaAct: [],
    nacionalidad: [null, [Validators.required]],
    paisOrigen: [null, [Validators.required]],
    pais: [null, [Validators.required]],
    estado: [{ value: null, disabled: true }, [Validators.required]],
    municipio: [{ value: null, disabled: true }, [Validators.required]],
    localidad: [{ value: null, disabled: true }],
    cp: [{ value: null, disabled: true }, [Validators.required]],
    puesto: [null, [Validators.required, Validators.maxLength(150)]],
    sucursal: [null, [Validators.required]],
  });

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    protected catNacionalidadService: CatNacionalidadService,
    protected catPaisService: CatPaisService,
    protected catEstadoService: CatEstadoService,
    protected catMunicipioService: CatMunicipioService,
    protected catLocalidadService: CatLocalidadService,
    protected catCPService: CatCPService,
    protected catSucursalService: CatSucursalService,
    protected utilService: UtilService,
    private fb: FormBuilder
  ) {
    this.NacionalidadEnum = NacionalidadesENUM;
    this.PaisEnum = PaisesENUM;
    this.datosUsuario = new DatosUsuarioDTO();
    this.domicilioUsuario = new DomicilioUsuarioDTO();
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        this.initDatosUsuario(user);
        this.initDomicilioUsuario(user);
        this.user.langKey = LanguagesENUM.ES;
        if (this.user.id === undefined) {
          this.user.activated = true;
        }
      }
    });
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });

    this.catNacionalidadService.query().subscribe((res: HttpResponse<ICatNacionalidad[]>) => (this.catnacionalidads = res.body || []));
    this.catPaisService.query({ page: 0, size: 250 }).subscribe((res: HttpResponse<ICatPais[]>) => {
      this.catpais = res.body || [];
      this.catpaisExt = this.catpais;
    });
    this.catEstadoService.query({ page: 0, size: 50 }).subscribe((res: HttpResponse<ICatEstado[]>) => (this.catestados = res.body || []));
    this.catSucursalService
      .query({ page: 0, size: 250 })
      .subscribe((res: HttpResponse<ICatSucursal[]>) => (this.catsucursales = res.body || []));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    if (this.editForm.valid) {
      this.isSaving = true;
      this.updateUser();
      if (this.user.id !== undefined) {
        this.userService.update(this.user).subscribe(
          () => this.onSaveSuccess(),
          () => this.onSaveError()
        );
      } else {
        this.userService.create(this.user).subscribe(
          () => this.onSaveSuccess(),
          () => this.onSaveError()
        );
      }
    }
  }

  private initDatosUsuario(user: any): void {
    this.user.fechaNacimiento = moment(this.user.fechaNacimiento);
    Object.assign(this.datosUsuario, {
      puesto: user.datosUsuario?.puesto,
      nacionalidadId: user.datosUsuario?.sucursal.id,
    });
  }

  private initDomicilioUsuario(user: any): void {
    Object.assign(this.domicilioUsuario, {
      nacionalidadId: user.domicilioUsuario?.nacionalidad.id,
      paisOrigenId: user.domicilioUsuario?.paisOrigen.id,
      paisId: user.domicilioUsuario?.pais.id,
      estadoId: user.domicilioUsuario?.estado.id,
      municipioId: user.domicilioUsuario?.municipio.id,
      localidadId: user.domicilioUsuario?.localidad.id,
      cpId: user.domicilioUsuario?.cp.id,
      colonia: user.domicilioUsuario?.colonia,
      calle: user.domicilioUsuario?.calle,
      noExt: user.domicilioUsuario?.noExt,
      noInt: user.domicilioUsuario?.noInt,
    });

    this.enableControlsPais();
    this.getMunicipios();
    this.getLocalidades();
    this.getCPs();
  }

  private updateUser(): void {
    this.updateDatosUsuario();
    this.updateDomicilioUsuario();
    // eslint-disable-next-line no-console
    console.log('datos', this.user);
  }

  private updateDatosUsuario(): void {
    if (isNullOrUndefined(this.user.datosUsuario)) {
      this.user.datosUsuario = new DatosUsuario();
    }

    Object.assign(this.user.datosUsuario, {
      puesto: this.datosUsuario.puesto,
      sucursal: this.catsucursales.find(ele => ele.id === this.datosUsuario.sucursalId),
    });
  }

  private updateDomicilioUsuario(): void {
    if (isNullOrUndefined(this.user.domicilioUsuario)) {
      this.user.domicilioUsuario = new DomicilioUsuario();
    }

    Object.assign(this.user.domicilioUsuario, {
      colonia: this.domicilioUsuario.colonia,
      calle: this.domicilioUsuario.calle,
      noExt: this.domicilioUsuario.noExt,
      noInt: this.domicilioUsuario.noInt,
      domicilio: this.domicilioUsuario.domicilio,
      nacionalidad: this.catnacionalidads.find(ele => ele.id === this.domicilioUsuario.nacionalidadId),
      paisOrigen: this.catpais.find(ele => ele.id === this.domicilioUsuario.paisOrigenId),
      pais: this.catpais.find(ele => ele.id === this.domicilioUsuario.paisId),
      estado: this.catestados.find(ele => ele.id === this.domicilioUsuario.estadoId),
      municipio: this.catmunicipios.find(ele => ele.id === this.domicilioUsuario.municipioId),
      localidad: this.catlocalidads.find(ele => ele.id === this.domicilioUsuario.localidadId),
      cp: this.catcps.find(ele => ele.id === this.domicilioUsuario.cpId),
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  private enableControlsPais(): void {
    if (this.domicilioUsuario.paisId === PaisesENUM.MEXICO) {
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
      this.domicilioUsuario.localidadId = undefined;
      this.catlocalidads = [];
      this.domicilioUsuario.cpId = undefined;
      this.catcps = [];

      if (divGeoPolitica >= ORDEN_DIV_GEOPOLITICA.ESTADO) {
        this.domicilioUsuario.municipioId = undefined;
        this.catmunicipios = [];
      }

      if (divGeoPolitica >= ORDEN_DIV_GEOPOLITICA.PAIS) {
        this.domicilioUsuario.estadoId = undefined;
        this.domicilioUsuario.municipioId = undefined;
        this.catmunicipios = [];
      }
    }
  }

  private getMunicipios(): void {
    const param: any = { 'estadoId.equals': this.domicilioUsuario.estadoId, page: 0, size: 250 };
    this.catMunicipioService
      .query(param)
      .pipe(
        map((res: HttpResponse<ICatMunicipio[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatMunicipio[]) => (this.catmunicipios = resBody));
  }

  private getLocalidades(): void {
    const param: any = {
      'estadoId.equals': this.domicilioUsuario.estadoId,
      'municipioId.equals': this.domicilioUsuario.municipioId,
      page: 0,
      size: 250,
    };

    this.catLocalidadService
      .query(param)
      .pipe(
        map((res: HttpResponse<ICatLocalidad[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatLocalidad[]) => (this.catlocalidads = resBody));
  }

  private getCPs(): void {
    const param: any = {
      'estadoId.equals': this.domicilioUsuario.estadoId,
      'municipioId.equals': this.domicilioUsuario.municipioId,
      page: 0,
      size: 250,
    };

    this.catCPService
      .query(param)
      .pipe(
        map((res: HttpResponse<ICatCP[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatCP[]) => (this.catcps = resBody));
  }

  onNacionalidad(): void {
    this.domicilioUsuario.paisOrigenId = undefined;
    if (this.domicilioUsuario.nacionalidadId === NacionalidadesENUM.MEXICANO) {
      this.domicilioUsuario.paisOrigenId = PaisesENUM.MEXICO;
      this.catpaisExt = this.catpais;
    } else {
      this.catpaisExt = this.catpais.filter(ele => ele.id !== PaisesENUM.MEXICO);
    }
  }

  onPais(): void {
    this.resetUbicacion(ORDEN_DIV_GEOPOLITICA.PAIS);
    this.enableControlsPais();
  }

  onEstado(): void {
    this.resetUbicacion(ORDEN_DIV_GEOPOLITICA.ESTADO);
    this.getMunicipios();
  }

  onMunicipio(): void {
    this.resetUbicacion(ORDEN_DIV_GEOPOLITICA.MUNICIPIO);
    this.getLocalidades();
    this.getCPs();
  }
}
