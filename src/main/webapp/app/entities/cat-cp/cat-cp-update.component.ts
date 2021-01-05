import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatCP, CatCP } from '../../shared/model/cat-cp.model';
import { CatCPService } from './cat-cp.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { ICatEstado } from '../../shared/model/cat-estado.model';
import { CatEstadoService } from '../../entities/cat-estado/cat-estado.service';
import { ICatMunicipio } from '../../shared/model/cat-municipio.model';
import { CatMunicipioService } from '../../entities/cat-municipio/cat-municipio.service';
import { ICatRiesgo } from '../../shared/model/cat-riesgo.model';
import { CatRiesgoService } from '../../entities/cat-riesgo/cat-riesgo.service';
import { map } from 'rxjs/operators';

type SelectableEntity = IUser | ICatEstado | ICatMunicipio | ICatRiesgo;

@Component({
  selector: 'jhi-cat-cp-update',
  templateUrl: './cat-cp-update.component.html',
})
export class CatCPUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  catestados: ICatEstado[] = [];
  catmunicipios: ICatMunicipio[] = [];
  catriesgos: ICatRiesgo[] = [];
  selEstado: any = null;
  selMunicipio: any = null;

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(5)]],
    anio: [],
    usuario: [null, Validators.required],
    estado: [null, Validators.required],
    municipio: [null, Validators.required],
    riesgo: [null, Validators.required],
  });

  constructor(
    protected catCPService: CatCPService,
    protected userService: UserService,
    protected catEstadoService: CatEstadoService,
    protected catMunicipioService: CatMunicipioService,
    protected catRiesgoService: CatRiesgoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catCP }) => {
      this.updateForm(catCP);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.catEstadoService.query().subscribe((res: HttpResponse<ICatEstado[]>) => (this.catestados = res.body || []));

      // this.catMunicipioService.query().subscribe((res: HttpResponse<ICatMunicipio[]>) => (this.catmunicipios = res.body || []));

      this.catRiesgoService.query().subscribe((res: HttpResponse<ICatRiesgo[]>) => (this.catriesgos = res.body || []));
    });
  }

  updateForm(catCP: ICatCP): void {
    this.editForm.patchValue({
      id: catCP.id,
      anio: catCP.anio,
      usuario: new User(),
      estado: catCP.estado,
      municipio: catCP.municipio,
      riesgo: catCP.riesgo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catCP = this.createFromForm();
    if (catCP.id !== undefined) {
      this.subscribeToSaveResponse(this.catCPService.update(catCP));
    } else {
      this.subscribeToSaveResponse(this.catCPService.create(catCP));
    }
  }

  private createFromForm(): ICatCP {
    return {
      ...new CatCP(),
      id: this.editForm.get(['id'])!.value,
      anio: this.editForm.get(['anio'])!.value,
      usuario: new User(),
      estado: this.editForm.get(['estado'])!.value,
      municipio: this.editForm.get(['municipio'])!.value,
      riesgo: this.editForm.get(['riesgo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatCP>>): void {
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
