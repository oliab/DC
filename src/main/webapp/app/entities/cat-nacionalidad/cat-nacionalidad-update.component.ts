import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatNacionalidad, CatNacionalidad } from 'app/shared/model/cat-nacionalidad.model';
import { CatNacionalidadService } from './cat-nacionalidad.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cat-nacionalidad-update',
  templateUrl: './cat-nacionalidad-update.component.html',
})
export class CatNacionalidadUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    nacionalidad: [null, [Validators.required, Validators.maxLength(100)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catNacionalidadService: CatNacionalidadService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catNacionalidad }) => {
      this.updateForm(catNacionalidad);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catNacionalidad: ICatNacionalidad): void {
    this.editForm.patchValue({
      id: catNacionalidad.id,
      nacionalidad: catNacionalidad.nacionalidad,
      usuario: catNacionalidad.usuario,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catNacionalidad = this.createFromForm();
    if (catNacionalidad.id !== undefined) {
      this.subscribeToSaveResponse(this.catNacionalidadService.update(catNacionalidad));
    } else {
      this.subscribeToSaveResponse(this.catNacionalidadService.create(catNacionalidad));
    }
  }

  private createFromForm(): ICatNacionalidad {
    return {
      ...new CatNacionalidad(),
      id: this.editForm.get(['id'])!.value,
      nacionalidad: this.editForm.get(['nacionalidad'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatNacionalidad>>): void {
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
