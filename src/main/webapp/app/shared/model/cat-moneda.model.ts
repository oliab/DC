import { IUser } from 'app/core/user/user.model';
import { ICatPais } from 'app/shared/model/cat-pais.model';

export interface ICatMoneda {
  id?: number;
  moneda?: string;
  usuario?: IUser;
  pais?: ICatPais;
}

export class CatMoneda implements ICatMoneda {
  constructor(public id?: number, public moneda?: string, public usuario?: IUser, public pais?: ICatPais) {}
}
