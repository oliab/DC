import { Injectable } from '@angular/core';
import { isNullOrUndefined } from 'util';
import * as format from 'string-format';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function equalsControlsFormValues(control1: string, control2: string): ValidatorFn {
  return (fGroup: AbstractControl): ValidationErrors | null => {
    const val1 = fGroup.get([control1]);
    const val2 = fGroup.get([control2]);
    if (val1?.value !== val2?.value) {
      val2?.setErrors({ notEquivalent: true });
      return { notEquivalent: true };
    }
    return null;
  };
}

@Injectable()
export class FormValidatorService {
  errorControls = {
    required: 'Información requerida',
    maxlength: 'Se superó la longitud máxima permitida de {requiredLength} caracteres.',
    minlength: '{actualLength} de {requiredLength} caracteres mínimos requeridos',
    email: 'Correo electrónico inválido',
    pattern: 'Formato inválido',
    notEquivalent: 'Confirmación de contraseña no válida',
  };

  ValidateControl(validatorName: string, validatorValue?: any, customMessages?: any): string {
    let error = null;
    if (!isNullOrUndefined(customMessages)) {
      error = this.getError(customMessages, validatorName, validatorValue);
    }

    return error || this.getError(this.errorControls, validatorName, validatorValue);
  }

  private getError(errorControls: any, key: string, vars: any): string {
    const error = errorControls[key];
    return vars && typeof vars === 'object' && error ? format(error, vars) : error;
  }
}
