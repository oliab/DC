import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatPais, CatPais } from 'app/shared/model/cat-pais.model';
import { CatPaisService } from './cat-pais.service';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cat-pais-update',
  templateUrl: './cat-pais-update.component.html',
})
export class CatPaisUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(3)]],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    codigoA2: [null, [Validators.required, Validators.maxLength(2)]],
    codigoA3: [null, [Validators.required, Validators.maxLength(3)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catPaisService: CatPaisService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catPais }) => {
      this.updateForm(catPais);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catPais: ICatPais): void {
    this.editForm.patchValue({
      id: catPais.id,
      nombre: catPais.nombre,
      codigoA2: catPais.codigoA2,
      codigoA3: catPais.codigoA3,
      usuario: new User(),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catPais = this.createFromForm();
    if (catPais.id !== undefined) {
      this.subscribeToSaveResponse(this.catPaisService.update(catPais));
    } else {
      this.subscribeToSaveResponse(this.catPaisService.create(catPais));
    }
  }

  private createFromForm(): ICatPais {
    return {
      ...new CatPais(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      codigoA2: this.editForm.get(['codigoA2'])!.value,
      codigoA3: this.editForm.get(['codigoA3'])!.value,
      usuario: new User(),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatPais>>): void {
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
