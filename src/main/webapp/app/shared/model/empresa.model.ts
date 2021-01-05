import { Moment } from 'moment';
import { ICatIdentificacion } from 'app/shared/model/cat-identificacion.model';
import { IUser } from 'app/core/user/user.model';
import { IDomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';

export interface IEmpresa {
  id?: number;
  fideicomiso?: boolean;
  rfc?: string;
  razonSocial?: string;
  noIdentificacion?: string;
  telefono?: string;
  fechaAlta?: Moment;
  fechaAct?: Moment;
  tipoIdentificacion?: ICatIdentificacion;
  usuarioAlta?: IUser;
  usuarioAct?: IUser;
  domicilio?: IDomicilioUsuario;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public fideicomiso?: boolean,
    public rfc?: string,
    public razonSocial?: string,
    public noIdentificacion?: string,
    public telefono?: string,
    public fechaAlta?: Moment,
    public fechaAct?: Moment,
    public tipoIdentificacion?: ICatIdentificacion,
    public usuarioAlta?: IUser,
    public usuarioAct?: IUser,
    public domicilio?: IDomicilioUsuario
  ) {
    this.fideicomiso = this.fideicomiso || false;
  }
}
