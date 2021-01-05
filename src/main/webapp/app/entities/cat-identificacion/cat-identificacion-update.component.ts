import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatIdentificacion, CatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { CatIdentificacionService } from './cat-identificacion.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cat-identificacion-update',
  templateUrl: './cat-identificacion-update.component.html',
})
export class CatIdentificacionUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    identificacion: [null, [Validators.required, Validators.maxLength(50)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catIdentificacionService: CatIdentificacionService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catIdentificacion }) => {
      this.updateForm(catIdentificacion);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catIdentificacion: ICatIdentificacion): void {
    this.editForm.patchValue({
      id: catIdentificacion.id,
      identificacion: catIdentificacion.identificacion,
      usuario: catIdentificacion.usuario,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catIdentificacion = this.createFromForm();
    if (catIdentificacion.id !== undefined) {
      this.subscribeToSaveResponse(this.catIdentificacionService.update(catIdentificacion));
    } else {
      this.subscribeToSaveResponse(this.catIdentificacionService.create(catIdentificacion));
    }
  }

  private createFromForm(): ICatIdentificacion {
    return {
      ...new CatIdentificacion(),
      id: this.editForm.get(['id'])!.value,
      identificacion: this.editForm.get(['identificacion'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatIdentificacion>>): void {
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
