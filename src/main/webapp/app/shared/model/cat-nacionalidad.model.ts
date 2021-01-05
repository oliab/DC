import { IUser } from 'app/core/user/user.model';

export interface ICatNacionalidad {
  id?: number;
  nacionalidad?: string;
  usuario?: IUser;
}

export class CatNacionalidad implements ICatNacionalidad {
  constructor(public id?: number, public nacionalidad?: string, public usuario?: IUser) {}
}
