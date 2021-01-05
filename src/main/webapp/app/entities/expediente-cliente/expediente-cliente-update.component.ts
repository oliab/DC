import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IExpedienteCliente, ExpedienteCliente } from 'app/shared/model/expediente-cliente.model';
import { ExpedienteClienteService } from './expediente-cliente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { ICatTipoDocumento } from 'app/shared/model/cat-tipo-documento.model';
import { CatTipoDocumentoService } from 'app/entities/cat-tipo-documento/cat-tipo-documento.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ICliente | ICatTipoDocumento | IUser;

@Component({
  selector: 'jhi-expediente-cliente-update',
  templateUrl: './expediente-cliente-update.component.html',
})
export class ExpedienteClienteUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];
  cattipodocumentos: ICatTipoDocumento[] = [];
  users: IUser[] = [];
  fechaAltaDp: any;
  fechaActDp: any;

  editForm = this.fb.group({
    id: [],
    empresarial: [null, [Validators.required]],
    descripcion: [null, [Validators.maxLength(250)]],
    documento: [null, [Validators.required]],
    documentoContentType: [],
    fechaAlta: [],
    fechaAct: [],
    cliente: [null, Validators.required],
    tipoDocumento: [null, Validators.required],
    usuarioAlta: [null, Validators.required],
    usuarioAct: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected expedienteClienteService: ExpedienteClienteService,
    protected clienteService: ClienteService,
    protected catTipoDocumentoService: CatTipoDocumentoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expedienteCliente }) => {
      this.updateForm(expedienteCliente);

      this.clienteService
        .query({ 'expedienteClienteId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ICliente[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICliente[]) => {
          if (!expedienteCliente.cliente || !expedienteCliente.cliente.id) {
            this.clientes = resBody;
          } else {
            this.clienteService
              .find(expedienteCliente.cliente.id)
              .pipe(
                map((subRes: HttpResponse<ICliente>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICliente[]) => (this.clientes = concatRes));
          }
        });

      this.catTipoDocumentoService.query().subscribe((res: HttpResponse<ICatTipoDocumento[]>) => (this.cattipodocumentos = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(expedienteCliente: IExpedienteCliente): void {
    this.editForm.patchValue({
      id: expedienteCliente.id,
      empresarial: expedienteCliente.empresarial,
      descripcion: expedienteCliente.descripcion,
      documento: expedienteCliente.documento,
      documentoContentType: expedienteCliente.documentoContentType,
      fechaAlta: expedienteCliente.fechaAlta,
      fechaAct: expedienteCliente.fechaAct,
      cliente: expedienteCliente.cliente,
      tipoDocumento: expedienteCliente.tipoDocumento,
      usuarioAlta: expedienteCliente.usuarioAlta,
      usuarioAct: expedienteCliente.usuarioAct,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('resaMxWebApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expedienteCliente = this.createFromForm();
    if (expedienteCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.expedienteClienteService.update(expedienteCliente));
    } else {
      this.subscribeToSaveResponse(this.expedienteClienteService.create(expedienteCliente));
    }
  }

  private createFromForm(): IExpedienteCliente {
    return {
      ...new ExpedienteCliente(),
      id: this.editForm.get(['id'])!.value,
      empresarial: this.editForm.get(['empresarial'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      documentoContentType: this.editForm.get(['documentoContentType'])!.value,
      documento: this.editForm.get(['documento'])!.value,
      fechaAlta: this.editForm.get(['fechaAlta'])!.value,
      fechaAct: this.editForm.get(['fechaAct'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
      tipoDocumento: this.editForm.get(['tipoDocumento'])!.value,
      usuarioAlta: this.editForm.get(['usuarioAlta'])!.value,
      usuarioAct: this.editForm.get(['usuarioAct'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpedienteCliente>>): void {
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
