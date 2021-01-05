import { IUser } from 'app/core/user/user.model';

export interface ICatTipoDocumento {
  id?: number;
  tipo?: string;
  usuario?: IUser;
}

export class CatTipoDocumento implements ICatTipoDocumento {
  constructor(public id?: number, public tipo?: string, public usuario?: IUser) {}
}
