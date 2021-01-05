import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatMoneda, CatMoneda } from '../../shared/model/cat-moneda.model';
import { CatMonedaService } from './cat-moneda.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { ICatPais } from '../../shared/model/cat-pais.model';
import { CatPaisService } from '../../entities/cat-pais/cat-pais.service';

type SelectableEntity = IUser | ICatPais;

@Component({
  selector: 'jhi-cat-moneda-update',
  templateUrl: './cat-moneda-update.component.html',
})
export class CatMonedaUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  catpais: ICatPais[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(3)]],
    moneda: [null, [Validators.required, Validators.maxLength(100)]],
    usuario: [null, Validators.required],
    pais: [null, Validators.required],
  });

  constructor(
    protected catMonedaService: CatMonedaService,
    protected userService: UserService,
    protected catPaisService: CatPaisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catMoneda }) => {
      this.updateForm(catMoneda);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.catPaisService.query({ page: 0, size: 250 }).subscribe((res: HttpResponse<ICatPais[]>) => (this.catpais = res.body || []));
    });
  }

  updateForm(catMoneda: ICatMoneda): void {
    this.editForm.patchValue({
      id: catMoneda.id,
      moneda: catMoneda.moneda,
      usuario: new User(),
      pais: catMoneda.pais,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catMoneda = this.createFromForm();
    if (catMoneda.id !== undefined) {
      this.subscribeToSaveResponse(this.catMonedaService.update(catMoneda));
    } else {
      this.subscribeToSaveResponse(this.catMonedaService.create(catMoneda));
    }
  }

  private createFromForm(): ICatMoneda {
    return {
      ...new CatMoneda(),
      id: this.editForm.get(['id'])!.value,
      moneda: this.editForm.get(['moneda'])!.value,
      usuario: new User(),
      pais: this.editForm.get(['pais'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatMoneda>>): void {
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
