import { Injectable } from '@angular/core';
import { isNullOrUndefined } from 'util';

@Injectable({ providedIn: 'root' })
export class FilterService {
  constructor() {}

  isEmptyValues(params: Array<any>): boolean {
    for (const param of params) {
      if (!isNullOrUndefined(param)) {
        return false;
      }
    }

    return true;
  }

  getReqParamsObject(reqParams: any, params: Array<any>, pnames: Array<string>): void {
    let idx = 0;
    for (const param of params) {
      if (!isNullOrUndefined(param)) {
        Object.assign(reqParams, {
          [pnames[idx]]: param,
        });
      }

      idx++;
    }
  }
}
