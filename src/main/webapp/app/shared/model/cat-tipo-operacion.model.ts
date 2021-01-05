import { IUser } from 'app/core/user/user.model';

export interface ICatTipoOperacion {
  id?: number;
  operacion?: string;
  descripcion?: string;
  usuario?: IUser;
}

export class CatTipoOperacion implements ICatTipoOperacion {
  constructor(public id?: number, public operacion?: string, public descripcion?: string, public usuario?: IUser) {}
}
