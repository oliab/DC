import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatSucursal, CatSucursal } from 'app/shared/model/cat-sucursal.model';
import { CatSucursalService } from './cat-sucursal.service';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cat-sucursal-update',
  templateUrl: './cat-sucursal-update.component.html',
})
export class CatSucursalUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  fechaActDp: any;

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    nombre: [null, [Validators.required, Validators.maxLength(150)]],
    direccion: [null, [Validators.maxLength(512)]],
    telefono: [null, [Validators.maxLength(50)]],
    fechaAct: [null],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catSucursalService: CatSucursalService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catSucursal }) => {
      this.updateForm(catSucursal);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catSucursal: ICatSucursal): void {
    this.editForm.patchValue({
      id: catSucursal.id,
      nombre: catSucursal.nombre,
      direccion: catSucursal.direccion,
      telefono: catSucursal.telefono,
      fechaAct: undefined,
      usuario: new User(),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catSucursal = this.createFromForm();
    if (catSucursal.id !== undefined) {
      this.subscribeToSaveResponse(this.catSucursalService.update(catSucursal));
    } else {
      this.subscribeToSaveResponse(this.catSucursalService.create(catSucursal));
    }
  }

  private createFromForm(): ICatSucursal {
    return {
      ...new CatSucursal(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      fechaAct: undefined,
      usuario: new User(),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatSucursal>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
