import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatEstado, CatEstado } from '../../shared/model/cat-estado.model';
import { CatEstadoService } from './cat-estado.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { ICatPais } from '../../shared/model/cat-pais.model';
import { CatPaisService } from '../../entities/cat-pais/cat-pais.service';

type SelectableEntity = IUser | ICatPais;

@Component({
  selector: 'jhi-cat-estado-update',
  templateUrl: './cat-estado-update.component.html',
})
export class CatEstadoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  catpais: ICatPais[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(2)]],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    usuario: [null, Validators.required],
    pais: [null, Validators.required],
  });

  constructor(
    protected catEstadoService: CatEstadoService,
    protected userService: UserService,
    protected catPaisService: CatPaisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catEstado }) => {
      this.updateForm(catEstado);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.catPaisService.query({ page: 0, size: 250 }).subscribe((res: HttpResponse<ICatPais[]>) => (this.catpais = res.body || []));
    });
  }

  updateForm(catEstado: ICatEstado): void {
    this.editForm.patchValue({
      id: catEstado.id,
      nombre: catEstado.nombre,
      usuario: new User(),
      pais: catEstado.pais,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catEstado = this.createFromForm();
    if (catEstado.id !== undefined) {
      this.subscribeToSaveResponse(this.catEstadoService.update(catEstado));
    } else {
      this.subscribeToSaveResponse(this.catEstadoService.create(catEstado));
    }
  }

  private createFromForm(): ICatEstado {
    return {
      ...new CatEstado(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      usuario: new User(),
      pais: this.editForm.get(['pais'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatEstado>>): void {
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
