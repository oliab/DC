import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatMunicipio, CatMunicipio } from '../../shared/model/cat-municipio.model';
import { CatMunicipioService } from './cat-municipio.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { ICatEstado } from '../../shared/model/cat-estado.model';
import { CatEstadoService } from '../../entities/cat-estado/cat-estado.service';

type SelectableEntity = IUser | ICatEstado;

@Component({
  selector: 'jhi-cat-municipio-update',
  templateUrl: './cat-municipio-update.component.html',
})
export class CatMunicipioUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  catestados: ICatEstado[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(5)]],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    clave: [null, [Validators.required, Validators.maxLength(3)]],
    usuario: [null, Validators.required],
    estado: [null, Validators.required],
  });

  constructor(
    protected catMunicipioService: CatMunicipioService,
    protected userService: UserService,
    protected catEstadoService: CatEstadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catMunicipio }) => {
      this.updateForm(catMunicipio);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.catEstadoService.query({ page: 0, size: 50 }).subscribe((res: HttpResponse<ICatEstado[]>) => (this.catestados = res.body || []));
    });
  }

  updateForm(catMunicipio: ICatMunicipio): void {
    this.editForm.patchValue({
      id: catMunicipio.id,
      nombre: catMunicipio.nombre,
      clave: catMunicipio.clave,
      usuario: new User(),
      estado: catMunicipio.estado,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catMunicipio = this.createFromForm();
    if (catMunicipio.id !== undefined) {
      this.subscribeToSaveResponse(this.catMunicipioService.update(catMunicipio));
    } else {
      this.subscribeToSaveResponse(this.catMunicipioService.create(catMunicipio));
    }
  }

  private createFromForm(): ICatMunicipio {
    return {
      ...new CatMunicipio(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      usuario: new User(),
      estado: this.editForm.get(['estado'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatMunicipio>>): void {
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
