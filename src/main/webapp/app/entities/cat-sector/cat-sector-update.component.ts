import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatSector, CatSector } from '../../shared/model/cat-sector.model';
import { CatSectorService } from './cat-sector.service';
import { IUser, User } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';

@Component({
  selector: 'jhi-cat-sector-update',
  templateUrl: './cat-sector-update.component.html',
})
export class CatSectorUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [null, [Validators.required, Validators.maxLength(15)]],
    actividadEconomica: [null, [Validators.required, Validators.maxLength(100)]],
    actividadVulnerable: [null, [Validators.required]],
    usuario: [null, Validators.required],
  });

  constructor(
    protected catSectorService: CatSectorService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catSector }) => {
      this.updateForm(catSector);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(catSector: ICatSector): void {
    this.editForm.patchValue({
      id: catSector.id,
      actividadEconomica: catSector.actividadEconomica,
      actividadVulnerable: catSector.actividadVulnerable,
      usuario: new User(),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catSector = this.createFromForm();
    if (catSector.id !== undefined) {
      this.subscribeToSaveResponse(this.catSectorService.update(catSector));
    } else {
      this.subscribeToSaveResponse(this.catSectorService.create(catSector));
    }
  }

  private createFromForm(): ICatSector {
    return {
      ...new CatSector(),
      id: this.editForm.get(['id'])!.value,
      actividadEconomica: this.editForm.get(['actividadEconomica'])!.value,
      actividadVulnerable: this.editForm.get(['actividadVulnerable'])!.value,
      usuario: new User(),
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatSector>>): void {
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
