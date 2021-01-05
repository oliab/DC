import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDatosUsuario, DatosUsuario } from 'app/shared/model/datos-usuario.model';
import { DatosUsuarioService } from './datos-usuario.service';
import { ICatSucursal } from 'app/shared/model/cat-sucursal.model';
import { CatSucursalService } from 'app/entities/cat-sucursal/cat-sucursal.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ICatSucursal | IUser;

@Component({
  selector: 'jhi-datos-usuario-update',
  templateUrl: './datos-usuario-update.component.html',
})
export class DatosUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  catsucursals: ICatSucursal[] = [];
  users: IUser[] = [];
  fechaActDp: any;

  editForm = this.fb.group({
    id: [],
    puesto: [null, [Validators.required, Validators.maxLength(150)]],
    fechaAct: [],
    sucursal: [],
    user: [null, Validators.required],
  });

  constructor(
    protected datosUsuarioService: DatosUsuarioService,
    protected catSucursalService: CatSucursalService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ datosUsuario }) => {
      this.updateForm(datosUsuario);

      this.catSucursalService.query().subscribe((res: HttpResponse<ICatSucursal[]>) => (this.catsucursals = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(datosUsuario: IDatosUsuario): void {
    this.editForm.patchValue({
      id: datosUsuario.id,
      puesto: datosUsuario.puesto,
      fechaAct: datosUsuario.fechaAct,
      sucursal: datosUsuario.sucursal,
      user: datosUsuario.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const datosUsuario = this.createFromForm();
    if (datosUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.datosUsuarioService.update(datosUsuario));
    } else {
      this.subscribeToSaveResponse(this.datosUsuarioService.create(datosUsuario));
    }
  }

  private createFromForm(): IDatosUsuario {
    return {
      ...new DatosUsuario(),
      id: this.editForm.get(['id'])!.value,
      puesto: this.editForm.get(['puesto'])!.value,
      fechaAct: this.editForm.get(['fechaAct'])!.value,
      sucursal: this.editForm.get(['sucursal'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatosUsuario>>): void {
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
