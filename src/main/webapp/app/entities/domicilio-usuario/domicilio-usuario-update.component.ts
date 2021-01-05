import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDomicilioUsuario, DomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';
import { DomicilioUsuarioService } from './domicilio-usuario.service';
import { ICatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';
import { CatNacionalidadService } from 'app/entities/cat-nacionalidad/cat-nacionalidad.service';
import { ICatPais } from 'app/shared/model/cat-pais.model';
import { CatPaisService } from 'app/entities/cat-pais/cat-pais.service';
import { ICatEstado } from 'app/shared/model/cat-estado.model';
import { CatEstadoService } from 'app/entities/cat-estado/cat-estado.service';
import { ICatMunicipio } from 'app/shared/model/cat-municipio.model';
import { CatMunicipioService } from 'app/entities/cat-municipio/cat-municipio.service';
import { ICatLocalidad } from 'app/shared/model/cat-localidad.model';
import { CatLocalidadService } from 'app/entities/cat-localidad/cat-localidad.service';
import { ICatCP } from 'app/shared/model/cat-cp.model';
import { CatCPService } from 'app/entities/cat-cp/cat-cp.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ICatNacionalidad | ICatPais | ICatEstado | ICatMunicipio | ICatLocalidad | ICatCP | IUser;

@Component({
  selector: 'jhi-domicilio-usuario-update',
  templateUrl: './domicilio-usuario-update.component.html',
})
export class DomicilioUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  catnacionalidads: ICatNacionalidad[] = [];
  catpais: ICatPais[] = [];
  catestados: ICatEstado[] = [];
  catmunicipios: ICatMunicipio[] = [];
  catlocalidads: ICatLocalidad[] = [];
  catcps: ICatCP[] = [];
  users: IUser[] = [];
  fechaActDp: any;

  editForm = this.fb.group({
    id: [],
    colonia: [null, [Validators.maxLength(100)]],
    calle: [null, [Validators.maxLength(100)]],
    noExt: [null, [Validators.maxLength(20)]],
    noInt: [null, [Validators.maxLength(20)]],
    domicilio: [null, [Validators.maxLength(512)]],
    fechaAct: [],
    nacionalidad: [null, Validators.required],
    paisOrigen: [null, Validators.required],
    pais: [null, Validators.required],
    estado: [],
    municipio: [],
    localidad: [],
    cp: [],
    user: [null, Validators.required],
  });

  constructor(
    protected domicilioUsuarioService: DomicilioUsuarioService,
    protected catNacionalidadService: CatNacionalidadService,
    protected catPaisService: CatPaisService,
    protected catEstadoService: CatEstadoService,
    protected catMunicipioService: CatMunicipioService,
    protected catLocalidadService: CatLocalidadService,
    protected catCPService: CatCPService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ domicilioUsuario }) => {
      this.updateForm(domicilioUsuario);

      this.catNacionalidadService.query().subscribe((res: HttpResponse<ICatNacionalidad[]>) => (this.catnacionalidads = res.body || []));

      this.catPaisService.query().subscribe((res: HttpResponse<ICatPais[]>) => (this.catpais = res.body || []));

      this.catEstadoService.query().subscribe((res: HttpResponse<ICatEstado[]>) => (this.catestados = res.body || []));

      this.catMunicipioService.query().subscribe((res: HttpResponse<ICatMunicipio[]>) => (this.catmunicipios = res.body || []));

      this.catLocalidadService.query().subscribe((res: HttpResponse<ICatLocalidad[]>) => (this.catlocalidads = res.body || []));

      this.catCPService.query().subscribe((res: HttpResponse<ICatCP[]>) => (this.catcps = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(domicilioUsuario: IDomicilioUsuario): void {
    this.editForm.patchValue({
      id: domicilioUsuario.id,
      colonia: domicilioUsuario.colonia,
      calle: domicilioUsuario.calle,
      noExt: domicilioUsuario.noExt,
      noInt: domicilioUsuario.noInt,
      domicilio: domicilioUsuario.domicilio,
      fechaAct: domicilioUsuario.fechaAct,
      nacionalidad: domicilioUsuario.nacionalidad,
      paisOrigen: domicilioUsuario.paisOrigen,
      pais: domicilioUsuario.pais,
      estado: domicilioUsuario.estado,
      municipio: domicilioUsuario.municipio,
      localidad: domicilioUsuario.localidad,
      cp: domicilioUsuario.cp,
      user: domicilioUsuario.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const domicilioUsuario = this.createFromForm();
    if (domicilioUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.domicilioUsuarioService.update(domicilioUsuario));
    } else {
      this.subscribeToSaveResponse(this.domicilioUsuarioService.create(domicilioUsuario));
    }
  }

  private createFromForm(): IDomicilioUsuario {
    return {
      ...new DomicilioUsuario(),
      id: this.editForm.get(['id'])!.value,
      colonia: this.editForm.get(['colonia'])!.value,
      calle: this.editForm.get(['calle'])!.value,
      noExt: this.editForm.get(['noExt'])!.value,
      noInt: this.editForm.get(['noInt'])!.value,
      domicilio: this.editForm.get(['domicilio'])!.value,
      fechaAct: this.editForm.get(['fechaAct'])!.value,
      nacionalidad: this.editForm.get(['nacionalidad'])!.value,
      paisOrigen: this.editForm.get(['paisOrigen'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      municipio: this.editForm.get(['municipio'])!.value,
      localidad: this.editForm.get(['localidad'])!.value,
      cp: this.editForm.get(['cp'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDomicilioUsuario>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
