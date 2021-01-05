import { IUser } from 'app/core/user/user.model';

export interface ICatIdentificacion {
  id?: number;
  identificacion?: string;
  usuario?: IUser;
}

export class CatIdentificacion implements ICatIdentificacion {
  constructor(public id?: number, public identificacion?: string, public usuario?: IUser) {}
}
