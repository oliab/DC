import { NgModule } from '@angular/core';
import { ResaMxWebSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { FormValidatorComponent } from './form-validator/form-validator.component';
import { NgxSelectModule } from 'ngx-select-ex';

@NgModule({
  imports: [ResaMxWebSharedLibsModule, NgxSelectModule],
  declarations: [
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    FormValidatorComponent,
  ],
  entryComponents: [LoginModalComponent],
  exports: [
    ResaMxWebSharedLibsModule,
    FindLanguageFromKeyPipe,
    AlertComponent,
    AlertErrorComponent,
    LoginModalComponent,
    HasAnyAuthorityDirective,
    FormValidatorComponent,
    NgxSelectModule,
  ],
})
export class ResaMxWebSharedModule {}
