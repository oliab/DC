import { Moment } from 'moment';
import { ICatSucursal } from 'app/shared/model/cat-sucursal.model';
import { IUser } from 'app/core/user/user.model';

export interface IDatosUsuario {
  id?: number;
  puesto?: string;
  fechaAct?: Moment;
  sucursal?: ICatSucursal;
  user?: IUser;
}

export class DatosUsuario implements IDatosUsuario {
  constructor(public id?: number, public puesto?: string, public fechaAct?: Moment, public sucursal?: ICatSucursal, public user?: IUser) {}
}

export class DatosUsuarioDTO {
  puesto?: string;
  sucursalId?: string;
}
