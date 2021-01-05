import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatTipoDocumento, CatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';
import { CatTipoDocumentoService } from './cat-tipo-documento.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cat-tipo-documento-update',
  templateUrl: './cat-tipo-documento-update.component.html',
})
export class CatTipoDocumentoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required, Validators.maxLength(100)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catTipoDocumentoService: CatTipoDocumentoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catTipoDocumento }) => {
      this.updateForm(catTipoDocumento);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catTipoDocumento: ICatTipoDocumento): void {
    this.editForm.patchValue({
      id: catTipoDocumento.id,
      tipo: catTipoDocumento.tipo,
      usuario: catTipoDocumento.usuario,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catTipoDocumento = this.createFromForm();
    if (catTipoDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.catTipoDocumentoService.update(catTipoDocumento));
    } else {
      this.subscribeToSaveResponse(this.catTipoDocumentoService.create(catTipoDocumento));
    }
  }

  private createFromForm(): ICatTipoDocumento {
    return {
      ...new CatTipoDocumento(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatTipoDocumento>>): void {
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
