import { IUser } from 'app/core/user/user.model';

export interface ICatTipoEmpresa {
  id?: number;
  tipo?: string;
  usuario?: IUser;
}

export class CatTipoEmpresa implements ICatTipoEmpresa {
  constructor(public id?: number, public tipo?: string, public usuario?: IUser) {}
}
