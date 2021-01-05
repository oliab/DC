import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatTipoOperacion, CatTipoOperacion } from '../../shared/model/cat-tipo-operacion.model';
import { CatTipoOperacionService } from './cat-tipo-operacion.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';

@Component({
  selector: 'jhi-cat-tipo-operacion-update',
  templateUrl: './cat-tipo-operacion-update.component.html',
})
export class CatTipoOperacionUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [null, Validators.required],
    operacion: [null, [Validators.required, Validators.maxLength(100)]],
    descripcion: [null, [Validators.maxLength(150)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catTipoOperacionService: CatTipoOperacionService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoOperacion }) => {
      this.updateForm(catTipoOperacion);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catTipoOperacion: ICatTipoOperacion): void {
    this.editForm.patchValue({
      id: catTipoOperacion.id,
      operacion: catTipoOperacion.operacion,
      descripcion: catTipoOperacion.descripcion,
      usuario: new User(),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catTipoOperacion = this.createFromForm();
    if (catTipoOperacion.id !== undefined) {
      this.subscribeToSaveResponse(this.catTipoOperacionService.update(catTipoOperacion));
    } else {
      this.subscribeToSaveResponse(this.catTipoOperacionService.create(catTipoOperacion));
    }
  }

  private createFromForm(): ICatTipoOperacion {
    return {
      ...new CatTipoOperacion(),
      id: this.editForm.get(['id'])!.value,
      operacion: this.editForm.get(['operacion'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      usuario: new User(),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatTipoOperacion>>): void {
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
