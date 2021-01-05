import { IUser } from 'app/core/user/user.model';
import { ICatPais } from 'app/shared/model/cat-pais.model';

export interface ICatEstado {
  id?: number;
  nombre?: string;
  usuario?: IUser;
  pais?: ICatPais;
}

export class CatEstado implements ICatEstado {
  constructor(public id?: number, public nombre?: string, public usuario?: IUser, public pais?: ICatPais) {}
}
