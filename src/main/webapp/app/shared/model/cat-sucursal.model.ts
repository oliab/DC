import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICatSucursal {
  id?: number;
  nombre?: string;
  direccion?: string;
  telefono?: string;
  fechaAct?: Moment;
  usuario?: IUser;
}

export class CatSucursal implements ICatSucursal {
  constructor(
    public id?: number,
    public nombre?: string,
    public direccion?: string,
    public telefono?: string,
    public fechaAct?: Moment,
    public usuario?: IUser
  ) {}
}
