import { IUser } from 'app/core/user/user.model';

export interface ICatTipoMonetario {
  id?: number;
  instrumento?: string;
  descripcion?: string;
  usuario?: IUser;
}

export class CatTipoMonetario implements ICatTipoMonetario {
  constructor(public id?: number, public instrumento?: string, public descripcion?: string, public usuario?: IUser) {}
}
