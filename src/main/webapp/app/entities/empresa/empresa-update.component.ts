import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { CatIdentificacionService } from 'app/entities/cat-identificacion/cat-identificacion.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IDomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';
import { DomicilioUsuarioService } from 'app/entities/domicilio-usuario/domicilio-usuario.service';

type SelectableEntity = ICatIdentificacion | IUser | IDomicilioUsuario;

@Component({
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html',
})
export class EmpresaUpdateComponent implements OnInit {
  isSaving = false;
  catidentificacions: ICatIdentificacion[] = [];
  users: IUser[] = [];
  domicilios: IDomicilioUsuario[] = [];
  fechaAltaDp: any;
  fechaActDp: any;

  editForm = this.fb.group({
    id: [],
    fideicomiso: [null, [Validators.required]],
    rfc: [null, [Validators.required, Validators.maxLength(15)]],
    razonSocial: [null, [Validators.required, Validators.maxLength(250)]],
    noIdentificacion: [null, [Validators.required, Validators.maxLength(100)]],
    telefono: [null, [Validators.maxLength(50)]],
    fechaAlta: [null, [Validators.required]],
    fechaAct: [null, [Validators.required]],
    tipoIdentificacion: [null, Validators.required],
    usuarioAlta: [null, Validators.required],
    usuarioAct: [null, Validators.required],
    domicilio: [null, Validators.required],
  });

  constructor(
    protected empresaService: EmpresaService,
    protected catIdentificacionService: CatIdentificacionService,
    protected userService: UserService,
    protected domicilioUsuarioService: DomicilioUsuarioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.updateForm(empresa);

      this.catIdentificacionService
        .query()
        .subscribe((res: HttpResponse<ICatIdentificacion[]>) => (this.catidentificacions = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.domicilioUsuarioService
        .query({ 'empresaId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IDomicilioUsuario[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDomicilioUsuario[]) => {
          if (!empresa.domicilio || !empresa.domicilio.id) {
            this.domicilios = resBody;
          } else {
            this.domicilioUsuarioService
              .find(empresa.domicilio.id)
              .pipe(
                map((subRes: HttpResponse<IDomicilioUsuario>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDomicilioUsuario[]) => (this.domicilios = concatRes));
          }
        });
    });
  }

  updateForm(empresa: IEmpresa): void {
    this.editForm.patchValue({
      id: empresa.id,
      fideicomiso: empresa.fideicomiso,
      rfc: empresa.rfc,
      razonSocial: empresa.razonSocial,
      noIdentificacion: empresa.noIdentificacion,
      telefono: empresa.telefono,
      fechaAlta: empresa.fechaAlta,
      fechaAct: empresa.fechaAct,
      tipoIdentificacion: empresa.tipoIdentificacion,
      usuarioAlta: empresa.usuarioAlta,
      usuarioAct: empresa.usuarioAct,
      domicilio: empresa.domicilio,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  private createFromForm(): IEmpresa {
    return {
      ...new Empresa(),
      id: this.editForm.get(['id'])!.value,
      fideicomiso: this.editForm.get(['fideicomiso'])!.value,
      rfc: this.editForm.get(['rfc'])!.value,
      razonSocial: this.editForm.get(['razonSocial'])!.value,
      noIdentificacion: this.editForm.get(['noIdentificacion'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      fechaAlta: this.editForm.get(['fechaAlta'])!.value,
      fechaAct: this.editForm.get(['fechaAct'])!.value,
      tipoIdentificacion: this.editForm.get(['tipoIdentificacion'])!.value,
      usuarioAlta: this.editForm.get(['usuarioAlta'])!.value,
      usuarioAct: this.editForm.get(['usuarioAct'])!.value,
      domicilio: this.editForm.get(['domicilio'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>): void {
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
