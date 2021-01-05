import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatTipoEmpresa, CatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';
import { CatTipoEmpresaService } from './cat-tipo-empresa.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cat-tipo-empresa-update',
  templateUrl: './cat-tipo-empresa-update.component.html',
})
export class CatTipoEmpresaUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required, Validators.maxLength(100)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catTipoEmpresaService: CatTipoEmpresaService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoEmpresa }) => {
      this.updateForm(catTipoEmpresa);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catTipoEmpresa: ICatTipoEmpresa): void {
    this.editForm.patchValue({
      id: catTipoEmpresa.id,
      tipo: catTipoEmpresa.tipo,
      usuario: catTipoEmpresa.usuario,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catTipoEmpresa = this.createFromForm();
    if (catTipoEmpresa.id !== undefined) {
      this.subscribeToSaveResponse(this.catTipoEmpresaService.update(catTipoEmpresa));
    } else {
      this.subscribeToSaveResponse(this.catTipoEmpresaService.create(catTipoEmpresa));
    }
  }

  private createFromForm(): ICatTipoEmpresa {
    return {
      ...new CatTipoEmpresa(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatTipoEmpresa>>): void {
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
