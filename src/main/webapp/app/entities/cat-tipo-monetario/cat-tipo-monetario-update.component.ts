import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatTipoMonetario, CatTipoMonetario } from '../../shared/model/cat-tipo-monetario.model';
import { CatTipoMonetarioService } from './cat-tipo-monetario.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';

@Component({
  selector: 'jhi-cat-tipo-monetario-update',
  templateUrl: './cat-tipo-monetario-update.component.html',
})
export class CatTipoMonetarioUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(3)]],
    instrumento: [null, [Validators.required, Validators.maxLength(100)]],
    descripcion: [null, [Validators.maxLength(150)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catTipoMonetarioService: CatTipoMonetarioService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoMonetario }) => {
      this.updateForm(catTipoMonetario);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catTipoMonetario: ICatTipoMonetario): void {
    this.editForm.patchValue({
      id: catTipoMonetario.id,
      instrumento: catTipoMonetario.instrumento,
      descripcion: catTipoMonetario.descripcion,
      usuario: new User(),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catTipoMonetario = this.createFromForm();
    if (catTipoMonetario.id !== undefined) {
      this.subscribeToSaveResponse(this.catTipoMonetarioService.update(catTipoMonetario));
    } else {
      this.subscribeToSaveResponse(this.catTipoMonetarioService.create(catTipoMonetario));
    }
  }

  private createFromForm(): ICatTipoMonetario {
    return {
      ...new CatTipoMonetario(),
      id: this.editForm.get(['id'])!.value,
      instrumento: this.editForm.get(['instrumento'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      usuario: new User(),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatTipoMonetario>>): void {
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
