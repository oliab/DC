import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cat-pais',
        loadChildren: () => import('./cat-pais/cat-pais.module').then(m => m.ResaMxWebCatPaisModule),
      },
      {
        path: 'cat-estado',
        loadChildren: () => import('./cat-estado/cat-estado.module').then(m => m.ResaMxWebCatEstadoModule),
      },
      {
        path: 'cat-municipio',
        loadChildren: () => import('./cat-municipio/cat-municipio.module').then(m => m.ResaMxWebCatMunicipioModule),
      },
      {
        path: 'cat-riesgo',
        loadChildren: () => import('./cat-riesgo/cat-riesgo.module').then(m => m.ResaMxWebCatRiesgoModule),
      },
      {
        path: 'cat-cp',
        loadChildren: () => import('./cat-cp/cat-cp.module').then(m => m.ResaMxWebCatCPModule),
      },
      {
        path: 'cat-tipo-operacion',
        loadChildren: () => import('./cat-tipo-operacion/cat-tipo-operacion.module').then(m => m.ResaMxWebCatTipoOperacionModule),
      },
      {
        path: 'cat-sector',
        loadChildren: () => import('./cat-sector/cat-sector.module').then(m => m.ResaMxWebCatSectorModule),
      },
      {
        path: 'cat-localidad',
        loadChildren: () => import('./cat-localidad/cat-localidad.module').then(m => m.ResaMxWebCatLocalidadModule),
      },
      {
        path: 'cat-tipo-monetario',
        loadChildren: () => import('./cat-tipo-monetario/cat-tipo-monetario.module').then(m => m.ResaMxWebCatTipoMonetarioModule),
      },
      {
        path: 'cat-moneda',
        loadChildren: () => import('./cat-moneda/cat-moneda.module').then(m => m.ResaMxWebCatMonedaModule),
      },
      {
        path: 'cat-tipo-documento',
        loadChildren: () => import('./cat-tipo-documento/cat-tipo-documento.module').then(m => m.ResaMxWebCatTipoDocumentoModule),
      },
      {
        path: 'cat-tipo-empresa',
        loadChildren: () => import('./cat-tipo-empresa/cat-tipo-empresa.module').then(m => m.ResaMxWebCatTipoEmpresaModule),
      },
      {
        path: 'cat-nacionalidad',
        loadChildren: () => import('./cat-nacionalidad/cat-nacionalidad.module').then(m => m.ResaMxWebCatNacionalidadModule),
      },
      /* {
        path: 'datos-usuario',
        loadChildren: () => import('./datos-usuario/datos-usuario.module').then(m => m.ResaMxWebDatosUsuarioModule),
      },*/
      {
        path: 'cat-sucursal',
        loadChildren: () => import('./cat-sucursal/cat-sucursal.module').then(m => m.ResaMxWebCatSucursalModule),
      },
      {
        path: 'domicilio-usuario',
        loadChildren: () => import('./domicilio-usuario/domicilio-usuario.module').then(m => m.ResaMxWebDomicilioUsuarioModule),
      },
      {
        path: 'empresa',
        loadChildren: () => import('./empresa/empresa.module').then(m => m.ResaMxWebEmpresaModule),
      },
      {
        path: 'cat-identificacion',
        loadChildren: () => import('./cat-identificacion/cat-identificacion.module').then(m => m.ResaMxWebCatIdentificacionModule),
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.ResaMxWebClienteModule),
      },
      {
        path: 'expediente-cliente',
        loadChildren: () => import('./expediente-cliente/expediente-cliente.module').then(m => m.ResaMxWebExpedienteClienteModule),
      },
      {
        path: 'domicilio-empresa',
        loadChildren: () => import('./domicilio-empresa/domicilio-empresa.module').then(m => m.ResaMxWebDomicilioEmpresaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ResaMxWebEntityModule {}
