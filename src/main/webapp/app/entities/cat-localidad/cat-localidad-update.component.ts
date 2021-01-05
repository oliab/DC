import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatLocalidad, CatLocalidad } from '../../shared/model/cat-localidad.model';
import { CatLocalidadService } from './cat-localidad.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { ICatEstado } from '../../shared/model/cat-estado.model';
import { CatEstadoService } from '../../entities/cat-estado/cat-estado.service';
import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { CatMunicipioService } from '../../entities/cat-municipio/cat-municipio.service';
import { map } from 'rxjs/operators';

type SelectableEntity = IUser | ICatEstado | ICatMunicipio;

@Component({
  selector: 'jhi-cat-localidad-update',
  templateUrl: './cat-localidad-update.component.html',
})
export class CatLocalidadUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  catestados: ICatEstado[] = [];
  catmunicipios: ICatMunicipio[] = [];
  selEstado: any = null;
  selMunicipio: any = null;

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(9)]],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    clave: [null, [Validators.required, Validators.maxLength(4)]],
    usuario: [null, Validators.required],
    estado: [null, Validators.required],
    municipio: [null, Validators.required],
  });

  constructor(
    protected catLocalidadService: CatLocalidadService,
    protected userService: UserService,
    protected catEstadoService: CatEstadoService,
    protected catMunicipioService: CatMunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catLocalidad }) => {
      this.updateForm(catLocalidad);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.catEstadoService.query({ page: 0, size: 50 }).subscribe((res: HttpResponse<ICatEstado[]>) => (this.catestados = res.body || []));

      /* this.catMunicipioService
        .query({ page: 0, size: 250 })
        .subscribe((res: HttpResponse<ICatMunicipio[]>) => (this.catmunicipios = res.body || [])); */
    });
  }

  updateForm(catLocalidad: ICatLocalidad): void {
    this.editForm.patchValue({
      id: catLocalidad.id,
      nombre: catLocalidad.nombre,
      clave: catLocalidad.clave,
      usuario: new User(),
      estado: catLocalidad.estado,
      municipio: catLocalidad.municipio,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catLocalidad = this.createFromForm();
    if (catLocalidad.id !== undefined) {
      this.subscribeToSaveResponse(this.catLocalidadService.update(catLocalidad));
    } else {
      this.subscribeToSaveResponse(this.catLocalidadService.create(catLocalidad));
    }
  }

  private createFromForm(): ICatLocalidad {
    return {
      ...new CatLocalidad(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      clave: this.editForm.get(['clave'])!.value,
      usuario: new User(),
      estado: this.editForm.get(['estado'])!.value,
      municipio: this.editForm.get(['municipio'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatLocalidad>>): void {
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

  onEstado(): void {
    this.catmunicipios = [];
    const param: any = { 'estadoId.equals': this.selEstado.id, page: 0, size: 250 };
    this.catMunicipioService
      .query(param)
      .pipe(
        map((res: HttpResponse<ICatMunicipio[]>) => {
          return res.body ? res.body : [];
        })
      )
      .subscribe((resBody: ICatMunicipio[]) => (this.catmunicipios = resBody));
  }
}
