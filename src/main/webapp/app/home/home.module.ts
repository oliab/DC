import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgJhipsterModule } from 'ng-jhipster';

import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { IndexComponent } from 'app/index/index.component';

@NgModule({
  imports: [ResaMxWebSharedModule, NgbModule, NgJhipsterModule, FormsModule, ReactiveFormsModule, RouterModule.forChild(HOME_ROUTE)],
  declarations: [IndexComponent, HomeComponent],
})
export class ResaMxWebHomeModule {}
