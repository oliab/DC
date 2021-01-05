import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { ICatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';
import { CatTipoEmpresaService } from 'app/entities/cat-tipo-empresa/cat-tipo-empresa.service';
import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { CatIdentificacionService } from 'app/entities/cat-identificacion/cat-identificacion.service';
import { ICatSector } from 'app/shared/model/cat-sector.model';
import { CatSectorService } from 'app/entities/cat-sector/cat-sector.service';
import { ICatMoneda } from 'app/shared/model/cat-moneda.model';
import { CatMonedaService } from 'app/entities/cat-moneda/cat-moneda.service';

type SelectableEntity = IUser | IEmpresa | ICatTipoEmpresa | ICatIdentificacion | ICatSector | ICatMoneda;

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  empresas: IEmpresa[] = [];
  cattipoempresas: ICatTipoEmpresa[] = [];
  catidentificacions: ICatIdentificacion[] = [];
  catsectors: ICatSector[] = [];
  catmonedas: ICatMoneda[] = [];
  fechaAltaDp: any;
  fechaActDp: any;

  editForm = this.fb.group({
    id: [],
    noIdentificacion: [null, [Validators.required, Validators.maxLength(100)]],
    ingresos: [null, [Validators.required]],
    estimacionOperacion: [null, [Validators.required]],
    telefono: [null, [Validators.maxLength(50)]],
    fechaAlta: [null, [Validators.required]],
    fechaAct: [null, [Validators.required]],
    usuario: [null, Validators.required],
    empresa: [],
    tipoCliente: [null, Validators.required],
    tipoIdentificacion: [null, Validators.required],
    sector: [null, Validators.required],
    moneda: [null, Validators.required],
    usuarioAlta: [null, Validators.required],
    usuarioAct: [null, Validators.required],
  });

  constructor(
    protected clienteService: ClienteService,
    protected userService: UserService,
    protected empresaService: EmpresaService,
    protected catTipoEmpresaService: CatTipoEmpresaService,
    protected catIdentificacionService: CatIdentificacionService,
    protected catSectorService: CatSectorService,
    protected catMonedaService: CatMonedaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.empresaService
        .query({ 'clienteId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IEmpresa[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmpresa[]) => {
          if (!cliente.empresa || !cliente.empresa.id) {
            this.empresas = resBody;
          } else {
            this.empresaService
              .find(cliente.empresa.id)
              .pipe(
                map((subRes: HttpResponse<IEmpresa>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmpresa[]) => (this.empresas = concatRes));
          }
        });

      this.catTipoEmpresaService.query().subscribe((res: HttpResponse<ICatTipoEmpresa[]>) => (this.cattipoempresas = res.body || []));

      this.catIdentificacionService
        .query()
        .subscribe((res: HttpResponse<ICatIdentificacion[]>) => (this.catidentificacions = res.body || []));

      this.catSectorService.query().subscribe((res: HttpResponse<ICatSector[]>) => (this.catsectors = res.body || []));

      this.catMonedaService.query().subscribe((res: HttpResponse<ICatMoneda[]>) => (this.catmonedas = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      noIdentificacion: cliente.noIdentificacion,
      ingresos: cliente.ingresos,
      estimacionOperacion: cliente.estimacionOperacion,
      telefono: cliente.telefono,
      fechaAlta: cliente.fechaAlta,
      fechaAct: cliente.fechaAct,
      usuario: cliente.usuario,
      empresa: cliente.empresa,
      tipoCliente: cliente.tipoCliente,
      tipoIdentificacion: cliente.tipoIdentificacion,
      sector: cliente.sector,
      moneda: cliente.moneda,
      usuarioAlta: cliente.usuarioAlta,
      usuarioAct: cliente.usuarioAct,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      noIdentificacion: this.editForm.get(['noIdentificacion'])!.value,
      ingresos: this.editForm.get(['ingresos'])!.value,
      estimacionOperacion: this.editForm.get(['estimacionOperacion'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      fechaAlta: this.editForm.get(['fechaAlta'])!.value,
      fechaAct: this.editForm.get(['fechaAct'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
      empresa: this.editForm.get(['empresa'])!.value,
      tipoCliente: this.editForm.get(['tipoCliente'])!.value,
      tipoIdentificacion: this.editForm.get(['tipoIdentificacion'])!.value,
      sector: this.editForm.get(['sector'])!.value,
      moneda: this.editForm.get(['moneda'])!.value,
      usuarioAlta: this.editForm.get(['usuarioAlta'])!.value,
      usuarioAct: this.editForm.get(['usuarioAct'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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
