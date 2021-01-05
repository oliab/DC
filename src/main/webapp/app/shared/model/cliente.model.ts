import { Moment } from 'moment';
import { IUser, User } from 'app/core/user/user.model';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { ICatTipoEmpresa } from 'app/shared/model/cat-tipo-empresa.model';
import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { ICatSector } from 'app/shared/model/cat-sector.model';
import { ICatMoneda } from 'app/shared/model/cat-moneda.model';
import { DomicilioUsuarioDTO } from './domicilio-usuario.model';

export interface ICliente {
  id?: number;
  noIdentificacion?: string;
  ingresos?: number;
  estimacionOperacion?: number;
  telefono?: string;
  fechaAlta?: Moment;
  fechaAct?: Moment;
  usuario?: IUser;
  empresa?: IEmpresa;
  tipoCliente?: ICatTipoEmpresa;
  tipoIdentificacion?: ICatIdentificacion;
  sector?: ICatSector;
  moneda?: ICatMoneda;
  usuarioAlta?: IUser;
  usuarioAct?: IUser;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public noIdentificacion?: string,
    public ingresos?: number,
    public estimacionOperacion?: number,
    public telefono?: string,
    public fechaAlta?: Moment,
    public fechaAct?: Moment,
    public usuario?: IUser,
    public empresa?: IEmpresa,
    public tipoCliente?: ICatTipoEmpresa,
    public tipoIdentificacion?: ICatIdentificacion,
    public sector?: ICatSector,
    public moneda?: ICatMoneda,
    public usuarioAlta?: IUser,
    public usuarioAct?: IUser
  ) {}
}

export class DatosGeneralesCliente {
  id?: number;
  noIdentificacion?: string;
  ingresos?: number;
  estimacionOperacion?: number;
  telefono?: string;
  fechaAlta?: Moment;
  fechaAct?: Moment;
  usuario: IUser;
  tipoCliente?: ICatTipoEmpresa;
  tipoIdentificacion?: ICatIdentificacion;
  sector?: ICatSector;
  moneda?: ICatMoneda;
  tipoClienteId?: number;
  tipoIdentificacionId?: number;
  actividadEconomicaId?: string;
  monedaId?: string;
  comprobanteIdentificacion?: any;
  identificacionContentType: string;
  descripcionIdentificacion?: string;
  comprobanteIngresos?: any;
  comprobanteIngresosContentType: string;
  ingresosContentType: string;
  descripcionIngresos?: string;
  constructor() {
    this.usuario = new User();
    this.identificacionContentType = '';
    this.ingresosContentType = '';
    this.comprobanteIngresosContentType = '';
  }
}

export class DatosDireccionCliente {
  domicilioUsuario: DomicilioUsuarioDTO;
  comprobanteDomicilio?: any;
  domicilioContentType: string;
  descripcionDomicilio?: string;

  constructor() {
    this.domicilioUsuario = new DomicilioUsuarioDTO();
    this.domicilioContentType = '';
  }
}

export class DatosEmpresaCliente {
  id?: number;
  fideicomiso?: boolean;
  rfc?: string;
  razonSocial?: string;
  noIdentificacion?: string;
  telefono?: string;
  fechaAlta?: Moment;
  fechaAct?: Moment;
  usuarioAlta?: IUser;
  usuarioAct?: IUser;
  tipoIdentificacionId?: number;
  tipoIdentificacion?: ICatIdentificacion;
  comprobanteIdentificacion?: any;
  identificacionContentType: string;
  descripcionIdentificacion?: string;
  comprobanteFirma?: any;
  firmaContentType: string;
  descripcionFirma?: string;

  constructor() {
    this.identificacionContentType = '';
    this.firmaContentType = '';
  }
}

export class ClienteDTO {
  id?: number;
  datosGeneralesCliente: DatosGeneralesCliente;
  datosDireccionCliente: DatosDireccionCliente;
  datosEmpresaCliente: DatosEmpresaCliente;
  datosDireccionEmpresa: DatosDireccionCliente;

  constructor() {
    this.datosGeneralesCliente = new DatosGeneralesCliente();
    this.datosDireccionCliente = new DatosDireccionCliente();
    this.datosEmpresaCliente = new DatosEmpresaCliente();
    this.datosDireccionEmpresa = new DatosDireccionCliente();
  }
}
