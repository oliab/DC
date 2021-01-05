import { Component } from '@angular/core';
import { LoginService } from 'app/core/login/login.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'jhi-index',
  templateUrl: './index.component.html',
})
export class IndexComponent {
  authenticationError = false;
  loginForm: FormGroup;

  constructor(private loginService: LoginService, private router: Router, private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      username: [''],
      password: [''],
      rememberMe: [false],
    });
  }

  login(): void {
    this.loginService
      .login({
        username: this.loginForm.get('username')!.value,
        password: this.loginForm.get('password')!.value,
        rememberMe: this.loginForm.get('rememberMe')!.value,
      })
      .subscribe(
        () => {
          this.authenticationError = false;
          if (
            this.router.url === '/account/register' ||
            this.router.url.startsWith('/account/activate') ||
            this.router.url.startsWith('/account/reset/')
          ) {
            this.router.navigate(['']);
          } else {
            this.router.navigate(['panel']);
          }
        },
        () => (this.authenticationError = true)
      );
  }

  requestResetPassword(): void {
    this.router.navigate(['/account/reset', 'request']);
  }
}
