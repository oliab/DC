import { IUser } from 'app/core/user/user.model';

export interface ICatPais {
  id?: string;
  nombre?: string;
  codigoA2?: string;
  codigoA3?: string;
  usuario?: IUser;
}

export class CatPais implements ICatPais {
  constructor(public id?: string, public nombre?: string, public codigoA2?: string, public codigoA3?: string, public usuario?: IUser) {}
}
