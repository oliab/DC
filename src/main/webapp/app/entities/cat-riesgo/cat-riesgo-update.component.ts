import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatRiesgo, CatRiesgo } from '../../shared/model/cat-riesgo.model';
import { CatRiesgoService } from './cat-riesgo.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';

@Component({
  selector: 'jhi-cat-riesgo-update',
  templateUrl: './cat-riesgo-update.component.html',
})
export class CatRiesgoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required]],
    riesgo: [null, [Validators.required, Validators.maxLength(50)]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catRiesgoService: CatRiesgoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catRiesgo }) => {
      this.updateForm(catRiesgo);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catRiesgo: ICatRiesgo): void {
    this.editForm.patchValue({
      id: catRiesgo.id,
      riesgo: catRiesgo.riesgo,
      usuario: new User(),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catRiesgo = this.createFromForm();
    if (catRiesgo.id !== undefined) {
      this.subscribeToSaveResponse(this.catRiesgoService.update(catRiesgo));
    } else {
      this.subscribeToSaveResponse(this.catRiesgoService.create(catRiesgo));
    }
  }

  private createFromForm(): ICatRiesgo {
    return {
      ...new CatRiesgo(),
      id: this.editForm.get(['id'])!.value,
      riesgo: this.editForm.get(['riesgo'])!.value,
      usuario: new User(),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatRiesgo>>): void {
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
