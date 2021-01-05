import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class UtilService {
  constructor(protected dataUtils: JhiDataUtils, protected eventManager: JhiEventManager) {}

  enableControls(form: FormGroup, controls: Array<string>, flgEnable: boolean, flgReset?: boolean): void {
    for (const control of controls) {
      if (flgEnable) {
        form.get(control)?.enable();
      } else {
        form.get(control)?.disable();
      }
      if (flgReset) {
        form.get(control)?.reset();
      }
    }
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType || '	application/pdf', base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean, form: FormGroup): void {
    this.dataUtils.loadFileToForm(event, form, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('resaMxWebApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }
}
