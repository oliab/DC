import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgxUiLoaderModule, NgxUiLoaderConfig, NgxUiLoaderHttpModule } from 'ngx-ui-loader';

import './vendor';
import { ResaMxWebSharedModule } from 'app/shared/shared.module';
import { ResaMxWebCoreModule } from 'app/core/core.module';
import { ResaMxWebAppRoutingModule } from './app-routing.module';
import { ResaMxWebHomeModule } from './home/home.module';
import { ResaMxWebEntityModule } from './entities/entity.module';
import { ResaMxWebClientesModule } from './clientes/clientes.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  blur: 0,
  fgsColor: '#a5b44b',
  fgsSize: 90,
  pbColor: '#a5b44b',
  overlayColor: 'rgba(0,0,0,0.2)',
  logoPosition: 'bottom-right',
  logoUrl: '/content/images/imgHeader.svg',
  hasProgressBar: false,
};

@NgModule({
  imports: [
    BrowserModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
    NgxUiLoaderHttpModule.forRoot({ showForeground: true }),
    ResaMxWebSharedModule,
    ResaMxWebCoreModule,
    ResaMxWebHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ResaMxWebEntityModule,
    ResaMxWebClientesModule,
    ResaMxWebAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class ResaMxWebAppModule {}
