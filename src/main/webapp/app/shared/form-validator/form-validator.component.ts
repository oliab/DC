import { Component, ContentChild, Input } from '@angular/core';
import { FormValidatorService } from './form-validator.service';
import { NgControl } from '@angular/forms';

@Component({
  selector: 'jhi-form-validator',
  templateUrl: 'form-validator.component.html',
  providers: [FormValidatorService],
})
export class FormValidatorComponent {
  @ContentChild(NgControl, { static: true }) ngControl: any;
  @Input() customErrors: any;

  constructor(private formValidators: FormValidatorService) {}

  get errorControl(): any {
    if (this.ngControl) {
      for (const propertyName in this.ngControl.errors) {
        if (this.ngControl.touched || (this.ngControl._parent && this.ngControl._parent.submitted)) {
          return this.formValidators.ValidateControl(propertyName, this.ngControl.errors[propertyName], this.customErrors);
        }
      }
    }
    return null;
  }
}
