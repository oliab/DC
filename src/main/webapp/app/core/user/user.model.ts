import { IDatosUsuario } from '../../shared/model/datos-usuario.model';
import { IDomicilioUsuario } from 'app/shared/model/domicilio-usuario.model';
import { Moment } from 'moment';

export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  mLastName?: string;
  fechaNacimiento?: Moment;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
  datosUsuario?: IDatosUsuario;
  domicilioUsuario?: IDomicilioUsuario;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public mLastName?: string,
    public fechaNacimiento?: Moment,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string,
    public datosUsuario?: IDatosUsuario,
    public domicilioUsuario?: IDomicilioUsuario
  ) {}
}
